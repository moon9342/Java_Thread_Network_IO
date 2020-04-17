package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EXAM03_MultiEchoClient extends Application {
	
	private TextArea ta;
	private Button connBtn;  // 서버연결버튼
	private TextField tf;    // 채팅글 입력상자
	private Socket s;
	private BufferedReader br;
	private PrintWriter pr;
	
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
		
		connBtn = new Button("서버에 접속!!");
		connBtn.setPrefSize(250, 50);
		connBtn.setOnAction(e -> {
				// 연결되면 TextArea의 내용을 지워요!
				ta.clear();
				try {
					s = new Socket("localhost",9999);
					printMSG("서버 접속 성공!!");
					br = new BufferedReader(
							new InputStreamReader(
									s.getInputStream()));
					pr = new PrintWriter(
							s.getOutputStream());
					// 접속 성공했으니 입력상자 활성화
					tf.setDisable(false);					
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
			}		
		);
		
		tf = new TextField();
		tf.setPrefSize(400, 50);
		tf.setDisable(true);
		// 입력상자에서 글을 입력하고 enter를 치면 Action Event가 발생
		tf.setOnAction(e -> {
				String msg = tf.getText();  // 입력한 문자열을 가져와요!
				pr.println(msg);
				pr.flush();
				tf.clear();   // 채팅문자열을 지워요!
				
				if(!msg.equals("@EXIT")) {
					try {
						String revString = br.readLine();
						printMSG(revString);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					printMSG("[서버와의 연결종료]");
					tf.setDisable(true);
				}
			}		
		);
		
	
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(connBtn);
		flowpane.getChildren().add(tf);
		
		root.setBottom(flowpane);  // 전체 화면의 아래부분에 FlowPane 부착
		
		Scene scene = new Scene(root);  // BorderPane을 포함하는 장면생성
		primaryStage.setScene(scene);   // Window의 화면을 Scene으로 설정
		primaryStage.setTitle("에코프로그램");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();
	}
	
	public static void main(String[] args) {	
		launch();   // start() method가 호출되요!!

	}

}
