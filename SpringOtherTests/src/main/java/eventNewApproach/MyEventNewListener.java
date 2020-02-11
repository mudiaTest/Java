package eventNewApproach;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
 * Obiekt nas�uchoje i je�li pojawi si� event do ob�u�enia, to co� z nim robi
 */
@Component
public class MyEventNewListener  {
  
  @EventListener
  @Async // - ten event b�dzie wykonany w OSOBNYM w�tku 
  public void doYourWork(MyEventNew event) throws InterruptedException {
    System.out.println(Thread.currentThread().getId() + ": Wykonuj� event (1.1):" + event.stMessage);    
    Thread.sleep(1000);
    System.out.println(Thread.currentThread().getId() + ": Wykonuj� event (1.2):" + event.stMessage);    
    Thread.sleep(1000);
    System.out.println(Thread.currentThread().getId() + ": Wykonuj� event (1.3):" + event.stMessage);    
  }
}
