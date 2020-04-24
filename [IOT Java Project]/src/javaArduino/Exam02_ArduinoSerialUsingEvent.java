package javaArduino;

import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

// 이벤트를 처리하는 리스너객체를 만들기 위한 class를 정의
class SerialListener implements SerialPortEventListener {

	private InputStream in;
	
	SerialListener(InputStream in) {
		this.in = in;
	}
	
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// 이벤트가 발생하면 호출되는 method
		if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int k = in.available();  // k는 전달되는 데이터의 크기
				byte[] data = new byte[k];
				in.read(data,0,k);
				
				// 출력해보아요!
				System.out.print("받은데이터 : " + new String(data));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
}

public class Exam02_ArduinoSerialUsingEvent {

	public static void main(String[] args) {

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
					
					// Event처리를 통해서 데이터를 읽어보아요!
					serialPort.addEventListener(new SerialListener(in));
					serialPort.notifyOnDataAvailable(true);
					
				} else {
					System.out.println("Serial 통신만 가능해요!!");
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}		
	}
}
