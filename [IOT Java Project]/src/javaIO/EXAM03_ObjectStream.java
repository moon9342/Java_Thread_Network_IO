package javaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// DOS 모드로 작성할 꺼구요!!
// HashMap에 데이터를 저장해서 이 데이터를 File에 저장

public class EXAM03_ObjectStream {
		
	public static void main(String[] args) {
		
		// 1. 로직처리를 통해서 만들어진 데이터 구조를 준비!
		//    최종 결과 데이터가 HashMap으로 만들어졌다고 가정해요!!
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("1","홍길동");
		map.put("2","신사임당");
		map.put("3","강감찬");
		map.put("4","유관순");
		
		// 이 정보를 file에 저장하고 싶어요!
		// 홍길동,신사임당,강감찬,유관순
		// file에 어떤 방식으로 저장할지를 결정.(문자열형태로 저장한다고 가정)
		File file = new File("asset/StringData.txt");
		// 대표적인 출력 Stream : PrintWriter
		// 대표적인 입력 Stream : BufferedReader
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// 객체직렬화를 통해서 저장하기 원하는 객체를 Stream을 통해서 보낼 수 
			// 있어요!(Object Serialization)
			oos.writeObject(map);
			oos.flush();
			
			oos.close();
			fos.close();
			// PrintWriter는 문자열을 저장하기 위한 Stream 
			//PrintWriter pr = new PrintWriter(file);
			// println()은 통로에 데이터를 쓰는 작업
			// 실제데이터가 전달되지 않아요!!
//			pr.println("이것은 소리없는 아우성!!");			
//			pr.flush(); // 통로에서 실제 데이터를 보내요!
//			pr.close(); // 사용이 끝난 통로를 닫아요!
			// 단일문자열과 같은 형태의 간단한 형태의 데이터를 보낼때는
			// 문제될게 없어요!!
			// 내가 저장할 데이터가 자료구조형태로 되어 있어요!!
			// 자료구조 형태가 복잡하면 복잡할수록 데이터를 저장하기가 쉽지 않아요!!
			// 객체자체를 파일에 저장할 수 있어요!!
			// 스트림을 통해서 일반 문자열이 아닌 객체자체를 전달할 수 있어요!!
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}





