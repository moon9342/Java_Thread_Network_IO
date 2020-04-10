package javaThread;

// 1초마다 자신의 이름을 도스창에 출력하는 Thread를
// 만들어서 실행해 보아요!!
// 한 10번씩만 출력해 보아요!!
// 한번씩 번갈아서 출력을 할꺼예요!! => 순서를 제어할려고 해요!!
// 일반적인 방식으로는 할 수 없어요!! Thread Scheduler에 의해서
// Thread가 제어되기 때문에 할 수 없어요!!
// 특수한 method를 이용해서 Thread 실행 순서를 제어해 보아요!!
// wait(), notify(), notifyAll() method를 이용해서 제어해요!
// => 이 method는 반드시.... Critical Section에서만 사용이 가능!
// => Critical Section(임계영역) => 동기화 코드가 적용된 부분.

// 공용객체를 만들기 위한 class
class Shared {
	// Thread가 공용으로 사용하는 데이터와
	// Thread가 공용으로 사용하는 method가 존재 => 로직처리
	// synchronized keyword를 이용하면 monitor를 가져오게 되요!
	public synchronized void printNum() {
		// Thread의 이름과 숫자를 찍어주는 로직 처리!
		for(int i=0; i<10; i++) {
			System.out.println(i + " : " + 
					Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
				notify();  // wait으로 block되어있는 Thread를
				           // 깨우는 method
				wait();    // 자기가 가진 monitor를 반납하고
				           // 자신은 wait block시키는 method
			} catch (Exception e) {
				
			}			
		}	
	}
}

class PrintRunnable implements Runnable {
	private Shared obj;
	
	PrintRunnable() {}
	PrintRunnable(Shared obj) {
		this.obj = obj;
	}
	
	@Override
	public void run() {
		obj.printNum();
	}
}

public class EXAM04_ThreadWaitNotify {

	public static void main(String[] args) {
		
		Shared obj = new Shared();
		
		PrintRunnable r1 = new PrintRunnable(obj);
		PrintRunnable r2 = new PrintRunnable(obj);
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		// 객체가 가지는 method를 호출할 때 일반적으로 
		// blocking method를 사용해요!
		
		t1.start(); 
		t2.start();
		// start() blocking방식으로 동작하면??
		// 만약 blocking 방식의 method라면 순차처리가 되요!!
		// start() method는 non-blocking method!!
	
		
		
	}
}





