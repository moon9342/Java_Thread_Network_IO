package javaIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EXAM02_Notepad extends Application {
	
	private TextArea ta;
	private Button openBtn, saveBtn;
	
	private void printMSG(String msg) {
		Platform.runLater(() -> {						
			ta.appendText(msg + "\n");
		}					
	);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		ta = new TextArea();   // 글상자를 생성
		root.setCenter(ta);	  // BorderPane가운데에 TextArea 부착
		
		openBtn = new Button("파일 열기");
		openBtn.setPrefSize(250, 50);
		openBtn.setOnAction(e -> {
				// 파일열기 처리!!
			    // 1. textarea를 초기화
				ta.clear();
				// 2. Open할 파일을 선택해요!! => File Chooser를 이용
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open할 파일을 선택해주세요!");
				// 파일선택창에서 원하는 file을 선택한 후 open버튼을 누르면
				// File객체가 생성
				File file = fileChooser.showOpenDialog(primaryStage);
				// Open할 File을 선택했으면..
				// Stream을 생성해요!!
				try {
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					String line ="";
					while((line = br.readLine()) != null ) {
						printMSG(line);
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					
				}
			}		
		);
		
	
		// 일반 Panel하나를 생성할꺼예요!! => LinearLayout처럼 동작하는..
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(openBtn);  // FlowPane에 Button을 부착
		//flowpane.getChildren().add(saveBtn);  // FlowPane에 Button을 부착
		
		root.setBottom(flowpane);  // 전체 화면의 아래부분에 FlowPane 부착
		
		// Scene(장면) 필요해요!!
		Scene scene = new Scene(root);  // BorderPane을 포함하는 장면생성
		primaryStage.setScene(scene);   // Window의 화면을 Scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		// 화면에 창을 띄울려고 해요!	
		launch();   // start() method가 호출되요!!

	}

}
