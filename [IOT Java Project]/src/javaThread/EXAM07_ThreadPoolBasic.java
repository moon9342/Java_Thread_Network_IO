package javaThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*
 *  Pool 개념
 *  Java는 필요한 객체를 생성하고(new) -> Heap영역에 메모리가 할당되요!
 *  메모리를 이용해서 여러가지 처리를 해요!(데이터를 저장하고 method호출을 통해
 *  로직을 처리해요!!) -> 객체를 다 사용하고 더 이상 사용하지 않으면 
 *  Garbage collector가 사용하지 않는 객체를 메모리에서 제거.
 *  
 *  이런 일반적인 방식은 효율면에서 좋지 않아요!!
 *  
 *  사용할 객체를 일단 미리 많이 생성해서 모아놔요! Pool이라고 불리는 공간에 모아놔요!
 *  필요할때마다 Pool안에서 객체를 가져다가 사용해요. 객체를 사용한 후 
 *  사용이 끝나면 Pool에 반납하는 방식으로 사용.
 *  
 *  가장 대표적인 활용 => Database Connection Pool(DBCP)
 *                  Object Pool
 *                  Thread Pool 
 *                  
 *  Thread를 사용할 때 Thread t = new Thread(); 방식으로 사용했는데
 *  이번에는 Thread Pool을 이용해서 처리해 보아요!    
 *  ExecutorService라는 이름의 Thread Pool을 이용할꺼예요!              
 */

public class EXAM07_ThreadPoolBasic extends Application {
	
	private TextArea ta;
	private Button initBtn, startBtn, shutdownBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// shutdownBtn : Thread Pool을 종료하는 버튼
	// startBtn : Thread Pool안에서 Thread를 가져다가 사용하는 버튼
	private ExecutorService executorService;
	// ExecutorService : Thread Pool class
	
	// textarea에 문자열을 출력하기 위한 method
	private void printMSG(String msg) {
		Platform.runLater(() -> {						
			ta.appendText(msg + "\n");
		});		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		ta = new TextArea();   // 글상자를 생성
		root.setCenter(ta);	  // BorderPane가운데에 TextArea 부착
		
		initBtn = new Button("Thread Pool 생성");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(e -> {
				// java lambda를 이용한 event 처리 코드 작성
			   //executorService = Executors.newFixedThreadPool(5);
			   executorService =  Executors.newCachedThreadPool();
			   printMSG("Pool안의 Thread개수 : " + 
					   ((ThreadPoolExecutor)executorService).getPoolSize());
			}		
		);

		startBtn = new Button("Thread 생성");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(e -> {
				// Thread Pool에서 Thread를 가져다가 사용하는 코드
				// 10개의 Thread를 Thread pool에서 가져다가 사용해 보아요!
				for(int i=0; i<10; i++) {
					// 1. Runnable interface를 구현한 객체를 생성
					// 2. Thread Pool을 이용해서 Thread 생성.
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							String msg = "Thread Pool안의 개수 : " + 
									((ThreadPoolExecutor)executorService).getPoolSize();
							msg += ", Thread Name : " + Thread.currentThread().getName();
							printMSG(msg);
						}						
					};				
					executorService.execute(runnable);
					// new Thread()로 Thread를 생성해서 만드는 방식보다
					// Thread Pool을 이용하는게 일반적인 방식!!
				}
			}		
		);

		shutdownBtn = new Button("Thread Pool 종료");
		shutdownBtn.setPrefSize(250, 50);
		shutdownBtn.setOnAction(e -> {
			    // Thread Pool을 다 사용하고 이제 사용하지 않으면
			    // Thread Pool을 종료해줘야 해요!!
				executorService.shutdownNow();
			}		
		);
		
	
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(initBtn);  // FlowPane에 Button을 부착
		flowpane.getChildren().add(startBtn);  // FlowPane에 Button을 부착
		flowpane.getChildren().add(shutdownBtn);  // FlowPane에 Button을 부착
		
		root.setBottom(flowpane);  // 전체 화면의 아래부분에 FlowPane 부착
		
		// Scene(장면) 필요해요!!
		Scene scene = new Scene(root);  // BorderPane을 포함하는 장면생성
		primaryStage.setScene(scene);   // Window의 화면을 Scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {

		});
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		// 화면에 창을 띄울려고 해요!	
		launch();   // start() method가 호출되요!!

	}

}
