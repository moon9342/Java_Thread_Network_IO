package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EXAM02_EchoServer {

	public static void main(String[] args) {
		// 1. 서버프로그램이기때문에 ServerSocket이 필요
		//    클라이언트가 접속할 수 있는 포트번호를 가지고 ServerSocket을 생성
		try {
			ServerSocket server = new ServerSocket(7777);
			System.out.println("서버가 기동되었어요!!");
			// 2. 클라이언트가 접속할 수 있게 ServerSocket의 accept()호출
			Socket s = server.accept();
			// 3. 클라이언트로부터 데이터를 받아야 해요..그리고 받은 데이터를
			//    그래도 클라이언트에게 돌려줘야 해요!!
			// 데이터를 받기 위해서는 BufferedReader이용
			BufferedReader br = new BufferedReader(
					new InputStreamReader(s.getInputStream()));
			// 데이터를 보내기 위해서는 PrintWriter이용
			PrintWriter pr = new PrintWriter(s.getOutputStream());
			String msg = "";
			while(true) {
				// 클라이언트가 보내준 데이터를 받아요!
				msg = br.readLine();  // 비정상종료됬을때 exception
				if( (msg == null) || (msg.equals("@EXIT")) ) {
					break;
				}
				pr.println(msg);
				pr.flush();  // 버퍼를 비우고 데이터를 실제로 보내는 작업
			}
			// 리소스 정리
			if( pr != null ) pr.close();
			if( br != null ) br.close();
			if( s != null ) s.close();
			if( server != null ) server.close();
			System.out.println("서버가 종료되었어요!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}






