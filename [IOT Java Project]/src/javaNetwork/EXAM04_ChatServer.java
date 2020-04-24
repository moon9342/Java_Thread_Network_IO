package javaNetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

// Server쪽 프로그램

// Thread에 의해서 공유되는 공용객체를 만들기 위한 class를 정의
class ChatSharedObject {
	// Thread에 의해서 공유되어야 하는 데이터
	// 모든 클라이언트에 대한 Thread를 만들기위해 필요한 Runnable객체를 저장.
	List<ChatRunnable> clients = new ArrayList<ChatRunnable>();
	
	// 이 데이터를 제어하기 위해서 필요한 method
	// 새로운 사용자가 접속했을 때 clients안에 새로운 사용자에 대한 Runnable객체를 저장
	public void add(ChatRunnable r) {
		clients.add(r);
	}
	// 사용자가 접속을 종료했을 대 clients안에 있는 사용자를 삭제
	public void remove(ChatRunnable r) {
		clients.remove(r);
	}
	// 클라이언트가 데이터를 보내줬을 때 채팅메시지를 Broadcast하는 method
	public void   broadcast(String msg) {
		for(ChatRunnable client : clients) {
			client.getPr().println(msg);
			client.getPr().flush();
		}
	}	
}

class ChatRunnable implements Runnable {

	private Socket s;
	private BufferedReader br;
	private PrintWriter pr;
	private ChatSharedObject shared;
		
	public PrintWriter getPr() {
		return pr;
	}

	// constructor injection이라고 불리는 객체전달(주입)방식
	ChatRunnable(Socket s, ChatSharedObject shared) {
		this.s = s;
		this.shared = shared;
		
		try {
			this.br = new BufferedReader(
					new InputStreamReader(s.getInputStream()));
			this.pr = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Override
	public void run() {
		// Thread가 클라이언트와 어떻게 동작하는지를 여기에 명시.
		String line = "";
		try {
			
			while((line = br.readLine()) != null ) {
				if(line.equals("@EXIT")) {
					break;
				}
				// 자신과 연결된 클라이언트에게만 문자열을 전달
				//pr.println(line);
				//pr.flush();
				// 모든 클라이언트에게 문자열을 전달하기 위해서 공용객체를 활용
				shared.broadcast(line);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}

public class EXAM04_ChatServer extends Application {

	// 필요한 field를 선언
	private TextArea ta;
	private Button startBtn, stopBtn;  // 서버 시작버튼, 서버 중지버튼
	// Thread Pool을 생성(제한된 숫자의 Thread를 가지고 있는 pool이 아니라
	// 필요한 갯수만큼 Thread를 가지고 있는 Thread Pool을 생성)
	private ExecutorService excutorService = 
			Executors.newCachedThreadPool();
	// 공용객체를 하나 만들어요!(공용객체는 Thread에 의해서 공유되는 객체고 1개만 존재)
	private ChatSharedObject shared = new ChatSharedObject();
	
	// 서버쪽 네트워크 프로그램이니까 ServerSocket이 존재해야 해요!
	private ServerSocket server;
	
	// TextArea에 특정 문자열을 편하게 출력하기 위해서 하나의 method를 만들어서
	// 사용해요!
	private void printMSG(String msg) {
		// TextArea에 출력하기 위해서 Thread를 이용해야 해요!
		// Lambda 표현식으로 사용하는게 일반적이예요!
		Platform.runLater(() -> {
				ta.appendText(msg + "\n");				
			}
		);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 처음 창이 화면에 뜰때 화면구성하는 용도로 사용.
		// 화면을 동서남북중앙으로 구성하는 BorderPane을 이용
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);   
		
		// TextArea를 생성해서 BorderPane의 중앙에 부착
		ta = new TextArea();
		root.setCenter(ta);
		
		// 두개의 버튼을 각각 생성해서 화면에 부착
		startBtn = new Button("Echo Server 시작!!");
		startBtn.setPrefSize(150, 40);
		startBtn.setOnAction(e -> {
				// 서버시작 버튼을 눌렀을때 실행
				printMSG("[서버시작]");
				
				// 별도의 Thread를 만들어서 서버역할을 수행해야 해요!
				// Runnable interface를 구현한 객체를 만들어서
				// ExecutorService(Thread Pool)을 이용해서 Thread를 실행
				Runnable runnable = () -> {
					// ServerSocket이 있어야지 서버역할을 할 수 있어요
					try {
						server = new ServerSocket(9999);
						while(true) {
							Socket s = server.accept();
							ChatRunnable chat = new ChatRunnable(s,shared);
							// 공용객체에 새로운 사용자 추가
							shared.add(chat);
							
							excutorService.execute(chat);
						}						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					 					
				};
				excutorService.execute(runnable);  // Thread 실행
						
			}
		);
		
		stopBtn = new Button("Echo Server 중지!!");
		stopBtn.setPrefSize(150, 40);
		stopBtn.setOnAction(e -> {
				// Event 처리코드가 나와요!!
			}
		);
		
		// Android의 LinearLayout과 유사한 component
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 40);
		flowpane.setPadding(new Insets(10,10,10,10));
		flowpane.setHgap(10);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		
		root.setBottom(flowpane);
		
		// 화면구현이 끝났으니 창을 설정하고 화면에 show.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("멀티 Echo Server!!");
		primaryStage.setOnCloseRequest(e->{
			try {
				server.close();
				excutorService.shutdownNow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
