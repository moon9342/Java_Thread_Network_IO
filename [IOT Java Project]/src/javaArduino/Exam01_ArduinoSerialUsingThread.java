package javaArduino;

import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Exam01_ArduinoSerialUsingThread {

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
					
					// Thread를 이용해서 Arduino로부터 들어오는 데이터를
					// 반복적으로 받아요!
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							byte[] buffer = new byte[1024];
							int len = -1;
							try {
								while((len = in.read(buffer)) != -1) {
									System.out.print(
											"데이터 : " + new String(buffer,0,len));
								}
							} catch (Exception e) {
								System.out.println(e);
							}
						}
					});
					t.start();
				} else {
					// parallel port로 열렸을 경우
					System.out.println("Serial Port만 이용할 수 있어요!");
				}
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
				
				
	}
}
