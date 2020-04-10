package javaThread;

// 하나의 java파일내에서는 public class는 오직 1개만
// 존재할 수 있어요!
class MyRunnable implements Runnable {
	@Override
	public void run() {
		
	}
}

public class Thread_State {

	// class안에 class가 존재하는 inner class형태로
	// class를 정의할 수있어요!! => Android에서는 흔한일.
	// 일반적인 Java 프로그램에서는 왠만하면 지양.
	
	public static void main(String[] args) {
		// program의 시작 point
		// main method를 호출하는 주체가 => main thread
		// JVM이 하나의 Thread(main)를 내부적으로 생성해서
		// static method인 main()을 호출해서 프로그램이 시작
		
		// 별도의 Thread를 생성해서 사용해 볼꺼예요!
		// 1. Thread class를 상속받아서 class를 define
		//    생성한 후 실행
		//    이방식은 상속개념을 사용해서 객체사용에 제한.
		// 2. Runnable interface를 구현한 객체를 만들어서
		//    이 객체를 가지고 Thread를 생성한 후 사용.
		MyRunnable runnable = new MyRunnable();
		Thread t1 = new Thread(runnable);
		
		// Thread를 생성해서 이 Thread를 실행시키고 싶어요!
		// Thread를 실행시킬려면 반드시 start() method가 호출
		t1.start();
		
		
	}
}
