package javaNetwork;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

// 서버쪽 프로그램
// 클라이언트가 접속하면 현재 시간을 알아내서
// 클라이언트에게 전송하는 서버 프로그램

public class EXAM01_DateServer {

	// 프로그램의 entry point
	public static void main(String[] args) {
		
		// 1. 서버프로그램이니까
		//    클라이언트의 접속을 기다려야 해요!
		//    클라이언트의 Socket접속을 기다리는
		//    ServerSocket이 필요해요!
		//    적당한 port번호를 이용해서 ServerSocket객체를 생성
		try {
			ServerSocket server = new ServerSocket(5556);
			System.out.println("서버가 생성되었어요!!");
			// 2. 클라이언트의 접속을 기다리기 위한 method를 호출
			Socket s = server.accept();   // blocking method
			                   // 클라이언트가 접속할때까지 대기!!			
			System.out.println("클라이언트가 접속했어요!");
			// 3. 소켓이 생성되면 데이터 입출력하기 위해 Stream을 생성해요!
			// 현재시간구해요
			String date = (new Date()).toLocaleString();
			PrintWriter out = 
					new PrintWriter(s.getOutputStream());
			out.println(date);
			out.flush();
			out.close();     // stream을 닫고
			s.close();       // 클라이언트와 연결된 socket을 닫고 
			server.close();  // ServerSocket을 닫아요!
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}





