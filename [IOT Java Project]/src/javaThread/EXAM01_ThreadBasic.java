package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EXAM01_ThreadBasic extends Application {
	
	private TextArea ta;
	private Button btn;
	
	private void printMSG(String msg) {
		Platform.runLater(() -> {	
			// Thread.currentThread() : 현재 사용되는 thread의 
			// reference를 알아오는 방법.
			System.out.println(Thread.currentThread().getName());
			ta.appendText(msg + "\n");
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Thread.currentThread().getName());
		
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		ta = new TextArea();   // 글상자를 생성
		root.setCenter(ta);	  // BorderPane가운데에 TextArea 부착
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(e -> {
				printMSG("버튼 클릭클릭!!");
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
		// 현재 사용되는 Thread의 이름을 출력할꺼예요!!
	    System.out.println(Thread.currentThread().getName());
		launch();   // start() method가 호출되요!!

	}

}
