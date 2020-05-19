package javaCan;

import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

// JavaFX를 이용해서 구현해 보아요!
public class EXAM01_DataFrameSender extends Application {

	private TextArea textarea;
	private Button connBtn, sendBtn;
	
	private CommPortIdentifier portIdentifier;
	private CommPort commPort;
	private SerialPort serialPort;
	
	private OutputStream out;
	
	private void printMSG(String msg) {       // textarea에 메시지 
		                                      // 출력용도
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	// comm port연결하고 출력 stream 생성하는 부분.
	private void connectPort(String portName) {
		try {
			portIdentifier = 
					CommPortIdentifier.getPortIdentifier(portName);
			if(portIdentifier.isCurrentlyOwned()) {
				System.out.println("다른 프로그램에 의해서 포트가 사용중!!");
			} else {
				commPort = portIdentifier.open("PortOpen",4000);
				if( commPort instanceof SerialPort ) {
					serialPort = (SerialPort)commPort;
					serialPort.setSerialPortParams(
							9600, 
							SerialPort.DATABITS_8, 
							SerialPort.STOPBITS_1, 
							SerialPort.PARITY_NONE);
					// outputStream을 생성
					out = serialPort.getOutputStream();
					printMSG("포트 연결을 성공했어요!!");
				}
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Data Frame을 전송하는 method
	private void sendDataFrame(String msg) {
		// 데이터 프레임 전송
		// 프로토콜을 알아야지 그 형식대로 message를 만들어서 전송
		// 전달된 문자열은 CAN protocol에 맞춰서 만든 문자열 이예요!
		// CRC를 계산하기 위해서 약간의 처리를 해야해요!
		// msg = "W28000000061100002200000033"
		int checksumData = 0;
		msg = msg.toUpperCase();
		char c[] = msg.toCharArray();
		for( char cc : c) {
			checksumData += cc;
		}
		checksumData = (checksumData & 0xFF);
		// checksumData는 정수예요. 이걸 HexString으로 변환해서 붙여야 해요!
		// 최종 데이터 프레임은
		String sendMsg = ":" + msg + 
				Integer.toHexString(checksumData) + "\r";
		
		printMSG("보내려는 데이터는 : " + sendMsg);
		
		byte[] send = sendMsg.getBytes();
		try {
			out.write(send);      // 데이터 전송
			printMSG("성공적으로 전송되었습니다.!!");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		connBtn = new Button("COM포트 접속");
		connBtn.setPrefSize(200, 50);
		connBtn.setPadding(new Insets(10));
		connBtn.setOnAction(e -> {
			String PortNum = "COM13";
			connectPort(PortNum);
		});
		
		sendBtn = new Button("Data Frame 전송");
		sendBtn.setPrefSize(200, 50);
		sendBtn.setPadding(new Insets(10));
		sendBtn.setOnAction(e -> {
			// DataFrame 전송
			String msg = "W28000000061100002200000033";
			sendDataFrame(msg);
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.setHgap(10);
		flowpane.getChildren().add(connBtn);
		flowpane.getChildren().add(sendBtn);
	
		root.setBottom(flowpane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CAN 통신");
		primaryStage.setOnCloseRequest(e->{
			System.exit(0);
		});
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch();
	}

}
