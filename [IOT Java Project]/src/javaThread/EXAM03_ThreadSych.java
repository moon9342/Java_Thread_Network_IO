package javaThread;

// 공유객체를 만들기 위한 class. 
// 일반적으로 singleton으로 작성
class SharedObject {
	// Thread가 공유해서 사용하는 공유객체는
	// Thread가 사용하는 데이터와 로직을 포함하고 있어요!
	private int number;
	private Object monitor = new Object();  // monitor용 객체
	
	// Thread에 의해서 공유되는 field
	// 일반적으로 private으로 처리되기 때문에 이 field를
	// 이용하기 위해서 당연히 setter와 getter를 이용

	public int getNumber() {
		return number;
	}

	// 1번째 해결방법은 method호출을 순차적으로 처리!!
	// 각 Thread가 가지고 있는 공용객체의 method호출을 순차적으로 호출하게끔 처리
	// method자체가 동기화 처리가 되서 프로그래밍 하기는 쉬워요!!
	// 해당 method의 실행이 만약 오래걸리게 되면 performance에 문제가 발생!!
	// 전체 method를 동기화하는게 아니라 필요한 부분만 동기화 처리를 해요!
	
	public void setNumber(int number) {
		System.out.println("소리없는 아우성!!");
		synchronized (monitor) {
			this.number = number;
			try {
				// 현재 공유객체를 사용하는 Thread를 1초간 재워요!
				Thread.sleep(2000);
				System.out.println("현재 number : " + getNumber());
			} catch (Exception e) {
				// TODO: handle exception
			}			
		}
		System.out.println("이건또 먼가요??");
	}
	
}

class NumberRunnable implements Runnable {
	private SharedObject obj;
	private int number;
	
	// 기본 생성자는 항상 존재해야 해요!!
	NumberRunnable() {}
	
	// 공유객체를 받아들여서 저장하는 생성자를 작성해야 해요!
	// constructor injection
	NumberRunnable(SharedObject obj,int number) {
		this.obj = obj;
		this.number = number;
	}
	@Override
	public void run() {
		// 공유객체가 가지는 기능을 이용해서 숫자를 출력!!
		obj.setNumber(number);
	}	
}


public class EXAM03_ThreadSych {

	// 프로그램의 entry point
	public static void main(String[] args) {
		
		// Thread에 의해서 공유되는 공유객체 1개를 생성
		// 일반적으로 공유객체는 class로부터 객체가 딱 1개만
		// 생성되는 형태로 만들어져요! => Singleton pattern
		// 공유객체를 1개 만들어 보아요!!~
		// Thread는 로직처리를 공유객체를 이용해서 로직처리를하고
		// 데이터 처리 역시 공유객체를 통해서 처리
		// 공유객체를 만들기 위한 class define
		
		// Thread에 의해서 공유되는 객체를 하나 생성
		SharedObject obj = new SharedObject();
		
		// Thread를 생성하기 위해서 Runnable interface를 구현한 객체가 있어야 
		// 해요.
		// 그래서 "Runnable interface를 구현한 객체"를 만들기 위한 class를
		// define
		NumberRunnable r1 = new NumberRunnable(obj,100);
		NumberRunnable r2 = new NumberRunnable(obj,200);
		
		// Thread 2개를 생성
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		// Thread를 시작
		t1.start();
		t2.start();
		
	}
}







