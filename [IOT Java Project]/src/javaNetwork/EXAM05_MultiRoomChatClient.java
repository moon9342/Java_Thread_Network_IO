package javaNetwork;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// JavaFX를 이용해서 Client창을 띄워보아요!

public class EXAM05_MultiRoomChatClient extends Application {

	// Field 정의
	private String userID;   // 클라이언트 채팅 ID
	private TextArea textarea;  // 채팅창역할을 하는 TextArea
	private Button connBtn;  // 채팅서버와 연결을 하기 위한 버튼
	private Button disconnBtn;  // 채팅서버와 연결을 종료하기 위한 버튼
	private Button createRoomBtn;  // 새로운 채팅방을 만드는 버튼
	private Button connRoomBtn;    // 채팅방에 입장하기 위한 버튼
	private ListView<String> roomListView; // 채팅방 목록을 보여주는 listview
	// 채팅방에서 현재 채팅에 참여하고 있는 사람들의 목록을 보여주는 listview
	private ListView<String> participantsListView;  
	
	// TextArea에 내용을 출력하기 위해서 method를 정의
	private void printMSG(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane root = new BorderPane(); // 화면을 동서남북중앙 5개의
		                                    // 영역으로 분할
		root.setPrefSize(700, 500);  // 크기 조절
		
		// 화면중앙에 TextArea를 붙여요!
		textarea = new TextArea();
		textarea.setEditable(false);  // editing이 안되게끔 처리!
		root.setCenter(textarea);     // 화면 중앙에 textarea를 배치
		
		// 방목록을 표현하는 ListView를 생성
		roomListView = new ListView<String>();
		// 방안에서 채팅하는 사람들의 목록을 표현하는 ListView를 생성
		participantsListView = new ListView<String>();
		
		// 화면을 격자로 나누어서 component를 표현하는 layout
		GridPane gridpane = new GridPane();
		// gridpane의 padding(여백)부터 설정
		gridpane.setPadding(new Insets(10,10,10,10));
		// gridpane안에 여러 component가 붙는데 이 component간에 여백을 설정
		gridpane.setVgap(10);
		gridpane.add(roomListView, 0, 0);
		gridpane.add(participantsListView, 0, 1);
		
		root.setRight(gridpane); // 화면오른쪽에 GridPane을 부착
		
		// 창을띄우기 위한 코드
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MultiRoom Chat Client");
		primaryStage.setOnCloseRequest(e->{
			
		});
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}
	
}
