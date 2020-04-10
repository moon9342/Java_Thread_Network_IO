package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

// JavaFX를 이용한 화면 UI 생성
// JavaFX library가 있어야 해요! => 추가해야 해요!!
// 1. Application이라는 class를 상속해서 우리 class를 define해요!
// 2. Application이 가지고 있는 start() abstract method를 overriding.


public class EXAM05_ThreadInterrupt extends Application {
	
	private TextArea ta;
	private Button startBtn, stopBtn;
	private Thread countThread;
	
	// TextArea에 문자열을 출력하기 위한 method를 하나 만들어요!
	private void printMSG(String msg) {
		Platform.runLater(() -> {
				ta.appendText(msg + "\n");
			}			
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면을 구성하고 event 처리를 담당
		// 기본 layout을 생성 => BorderPane(동서남북중앙으로 구성)으로 생성.
		BorderPane root = new BorderPane();
		// BorderPane의 size를 설정(px단위로 가로길이와 세로길이를 설정)
		root.setPrefSize(700, 500);
		
		ta = new TextArea();   // 글상자를 생성
		root.setCenter(ta);	  // BorderPane가운데에 TextArea 부착
		
		startBtn = new Button("Thread 시작");
		startBtn.setPrefSize(250, 50);
		// Button을 클릭(Action)했을 때 이벤트 처리!!
		// Android에서 Event처리했던것과 유사한 방식으로 Event를 처리!!
		// 일반적으로 Java8에서 제공하는 Java Lambda식을 이용해서 작성.
		startBtn.setOnAction(e -> {
				// 클릭되면 Thread를 생성해서 동작시키는 코드가 나와요!
				countThread = new Thread(() -> {
						// run()의 작성!!
						// 1부터 100까지 1초마다 출력할꺼에요!!
						for(int i=0; i<100; i++) {
							// sleep()은 반드시 예외처리가 동반되야 해요!
							try {
								Thread.sleep(1000);
								// sleep()이 수행되었을 때
								// 만약 해당 Thread가 interrupt가
								// 표시되어 있으면 Exception이 발생!!!
								printMSG(i + "값이 출력되요!!");
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								//e1.printStackTrace();
								// break는 가장 근접한 loop를 벗어나는
								// java keyword
								break;
							}							
						}						
					}					
				);
				countThread.start();
			}		
		);

		stopBtn = new Button("Thread 중지");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(e -> {
				// 클릭되면 Thread를 중지시키는 코드가 나와요!!
				// 과거에는 stop() method를 이용해서 Thread를 중지시켰는데
			    // deprecated 되었어요!! (문제가 많아요!!)
				// 다른 method를 이용해서 중지시켜요!
				countThread.interrupt();
				// Thread에 interrupt가 수행되었다는 "표시"를 해요!
			}		
		);
		
	
		// 일반 Panel하나를 생성할꺼예요!! => LinearLayout처럼 동작하는..
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(startBtn);  
		flowpane.getChildren().add(stopBtn);
		
		root.setBottom(flowpane);  // 전체 화면의 아래부분에 FlowPane 부착
		
		// Scene(장면) 필요해요!!
		Scene scene = new Scene(root);  // BorderPane을 포함하는 장면생성
		primaryStage.setScene(scene);   // Window의 화면을 Scene으로 설정
		primaryStage.setTitle("Thread Interrupt 예제");
		primaryStage.setOnCloseRequest(e -> {
			// 이 창의 x버튼을 눌렀을 때 해야하는 일을 작성
			//System.exit(0); // 내부에 있는 모든 Thread를 강제종료시켜서
			                // 전체 프로그램을 종료!
		});
		primaryStage.show();
	}
	
	// program의 entry point
	public static void main(String[] args) {
		// 화면에 창을 띄울려고 해요!	
		launch();   // start() method가 호출되요!!

	}

}
