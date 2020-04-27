package javaArduino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam03_AndroidArduinoServer extends Application {
	
	private TextArea ta;
	private Button btn;
	private ServerSocket server;
	private BufferedReader br;
	private PrintWriter pr;
	
	private BufferedWriter bw;  // 아두이노에게 출력
	
	private void printMSG(String s) {
		Platform.runLater(() -> {						
			ta.appendText(s + "\n");
		});		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		ta = new TextArea();   // 글상자를 생성
		root.setCenter(ta);	  // BorderPane가운데에 TextArea 부착
		
		btn = new Button("서버 기동!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(e -> {
			// ServerSocket을 생성하고 Android로부터 데이터를 받아요!
			// Thread로 만들어 줘야 해요!
			Runnable r = new Runnable() {
				
				@Override
				public void run() {
					try {
						server = new ServerSocket(1234);
						printMSG("[서버소켓 기동]");
						Socket s = server.accept();
						printMSG("[클라이언트 접속]");
						br = new BufferedReader(
								new InputStreamReader(
										s.getInputStream()));
						pr = new PrintWriter(s.getOutputStream());
						String msg = "";
						while(true) {
							if((msg = br.readLine()) != null) {
								if(msg.equals("LED_ON")) {
									printMSG("불을 켜요!!");
									// 아두이노와의 스트림을 통해 데이터를
									// 보내면 되요!
									bw.write(msg,0,msg.length());
									bw.flush();
								}
								if(msg.equals("LED_OFF")) {
									printMSG("불을 꺼요!!");
									bw.write(msg,0,msg.length());
									bw.flush();
								}								
							}
						}
						
					} catch (Exception e2) {
						System.out.println(e2);
					}
				}
			};
			Thread t = new Thread(r);
			t.start();
			
			}		
		);
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(btn);  // FlowPane에 Button을 부착
		
		root.setBottom(flowpane);  // 전체 화면의 아래부분에 FlowPane 부착
		
		Scene scene = new Scene(root);  // BorderPane을 포함하는 장면생성
		primaryStage.setScene(scene);   // Window의 화면을 Scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();
	
		// Arduino와 Serial Port연결하는 코드를 넣어요!!
		CommPortIdentifier portIdentifier = null;
		try {
			// 1. Serial통신을 하기 위한 COM포트 설정
			portIdentifier = 
					CommPortIdentifier.getPortIdentifier("COM11");
			// 2. 포트가 사용되고 있는지 확인부터 해야 해요!
			if(portIdentifier.isCurrentlyOwned()) {
				System.out.println("포트가 사용중입니다. !!");
			} else {
				// Port객체를 얻어와요!
				CommPort commPort = 
						portIdentifier.open("PORT_OPEN",2000);
				
				// Port객체를 얻어온 후 우리가 사용하는건 SerialPort예요
				// Port에는 ParallelPort도 있어요!
				if( commPort instanceof SerialPort) {
					// SerialPort로 형변환(type casting)
					SerialPort serialPort = (SerialPort)commPort;
					// 포트 설정(통신속도같은걸 설정)
					serialPort.setSerialPortParams(
							9600,
							SerialPort.DATABITS_8, 
							SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					// 데이터 통신을 하기 위해서 Stream을 열어요!
					InputStream in = serialPort.getInputStream();
					OutputStream out = serialPort.getOutputStream();
					bw = new BufferedWriter(
							new OutputStreamWriter(out));
					
					// 데이터를 보낼려고 해요!!
				} else {
					System.out.println("Serial 통신만 가능해요!!");
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}	
	}
	
	public static void main(String[] args) {
		// 화면에 창을 띄울려고 해요!	
		launch();   // start() method가 호출되요!!

	}

}
