package spring.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest3 {
	
	static int t = 0;
	
	//@Scheduled(fixedRate = 500)
//	public void schedule3() throws InterruptedException {
//		System.out.println(Thread.currentThread().toString() + " -->"+t);
//		t++;
//		Thread.sleep(4000);
//	}
	
	public static Runnable r = new Runnable() {
    @Override
    public void run() {
    	System.out.println(Thread.currentThread().toString() + " ->"+t);
  		t++;	
  		try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
  };

}
