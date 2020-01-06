package spring.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest1 {
	
	int t = 0;
	
	@Autowired
	SecondPool sp;

	@Scheduled(fixedRate = 500000000)
	public void schedule2() {
//		System.out.println(Thread.currentThread().toString() + " ->"+t);
//		t++;
		System.out.println("Testing pool2");
		sp.doIt();
	}

}
