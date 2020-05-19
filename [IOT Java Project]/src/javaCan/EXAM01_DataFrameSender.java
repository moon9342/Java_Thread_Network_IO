package javaCan;

import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
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
	
	@Override
	public void start(Stage arg0) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		connBtn = new Button("COM포트 접속");
		connBtn.setPrefSize(200, 50);
		connBtn.setPadding(new Insets(10));
		connBtn.setOnAction(e -> {
			String PortNum = "COM15";
			connectPort(PortNum);
		});
		
		sendBtn = new Button("Data Frame 전송");
		sendBtn.setPrefSize(200, 50);
		sendBtn.setPadding(new Insets(10));
		sendBtn.setOnAction(e -> {
			// DataFrame 전송
		});
		
	}
	
	
	public static void main(String[] args) {
		launch();
	}

}
