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


public class EXAM00_JAVAFXUITemplate extends Application {
	
	private TextArea ta;
	private Button btn;
	
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
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		// Button을 클릭(Action)했을 때 이벤트 처리!!
		// Android에서 Event처리했던것과 유사한 방식으로 Event를 처리!!
		// 일반적으로 Java8에서 제공하는 Java Lambda식을 이용해서 작성.
		btn.setOnAction(e -> {
				//System.out.println("버튼이 클릭되었어요!!");
			    // 동기화가 일어나지 않아서 잘못된 결과를 나타낼 수 있어요! 
		        // 직접 UI component를 제어하는 방법은 좋지 않아요!!
				// ta.appendText("버튼이 클릭되었어요!!" + "\n");
			    // Thread를 이용해서 메시지 출력하는 방식으로 이용해야 해요!
			    // UI Component를 제어할 때 Thread를 이용해서 제어해야
			    // 동기화 문제를 해결할 수 있어요!
				Platform.runLater(() -> {						
						ta.appendText("버튼이 클릭되었어요!!" + "\n");
					}					
				);
			}		
		);
		
	
		// 일반 Panel하나를 생성할꺼예요!! => LinearLayout처럼 동작하는..
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
