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

public class EXAM02_ThreadSleep extends Application {
	
	private TextArea ta;
	private Button btn;
	
	// TextArea에 출력담당 method
	private void printMSG(String msg) {
		Platform.runLater(() -> {						
			ta.appendText(msg + "\n");
		}					
	);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		ta = new TextArea();   // 글상자를 생성
		root.setCenter(ta);	  // BorderPane가운데에 TextArea 부착
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		// Button을 누르면 Thread를 5개 생성.
		// 각 Thread는 1초마다 잤다 깼다 하면서 숫자를 하나 출력!!
		btn.setOnAction(e -> {
				// 5개의 Thread를 생성해야 되니까.. for문을 이용
				for(int i=0; i<5; i++) {
					Thread t = new Thread(() -> {
							try {
								for(int k=0; k<3; k++) {
									Thread.sleep(1000);
									printMSG(k + "-" + 
											Thread.currentThread().getName());									
								}
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}						
					);
					t.start();
				}
			}		
		);
		
	
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(btn);  // FlowPane에 Button을 부착
		
		root.setBottom(flowpane);  // 전체 화면의 아래부분에 FlowPane 부착
		
		// Scene(장면) 필요해요!!
		Scene scene = new Scene(root);  // BorderPane을 포함하는 장면생성
		primaryStage.setScene(scene);   // Window의 화면을 Scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		// 화면에 창을 띄울려고 해요!	
		launch();   // start() method가 호출되요!!

	}

}
