package spring.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest4 {
  
  int t = 0;
  
  //@Scheduled(fixedRate = 500)
  public void schedule3() throws InterruptedException {
    System.out.println(Thread.currentThread().toString() + " --->"+t);
    t++;
    Thread.sleep(4000);
  }

}
