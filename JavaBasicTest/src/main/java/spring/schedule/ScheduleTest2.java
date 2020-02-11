package spring.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest2 {
  
  static int t = 0;
  
  //@Scheduled(fixedRate = 500)
  public void schedule1() {
    System.out.println(Thread.currentThread().toString() + " >"+t);
    t++;    
  }
  
  
  public static Runnable r = new Runnable() {
    @Override
    public void run() {
      System.out.println(Thread.currentThread().toString() + " >"+t);
      t++;  
    }
  };
  
//  @Scheduled(fixedDelay = 1500)
//  public void schedule2() {
//    System.out.println("->"+t);
//    t++;
//  }

}
