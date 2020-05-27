package javaHadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Exam01_SimepleFileReadWrite {

	public static void main(String[] args) {
		
		// 1. Hadoop의 실행환경을 알아와야 해요!
		Configuration conf = new Configuration();
		
		try {
			FileSystem hdfs = FileSystem.get(conf);
			String fileName = "test.txt";
			// /user/root 디렉토리 아래에 파일이 생성되요!
			
			String contents = "소리없는 아우성!!";
			
			Path path = new Path(fileName);
			if( hdfs.exists(path) ) {
				// 똑같은 경로 및 파일이 존재하면 삭제
				hdfs.delete(path,true);
			}
			
			FSDataOutputStream out = hdfs.create(path);
			// 파일을 생성하고 outputStream을 리턴받아요!
			out.writeUTF(contents);
			out.close();
			
			// 만들어진 파일에서 데이터를 읽어보아요!
			FSDataInputStream in = hdfs.open(path);
			String data = in.readUTF();
			in.close();
			
			System.out.println("읽은 내용은 : " + data);
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
