package eventNewApproach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/*
 * Obiekt nas�uchuje i je�li pojawi si� event do ob�u�enia, to co� z nim robi
 */
@Component
public class MyEventNewListener2 {

  @Autowired
  private ApplicationEventPublisher aep;
  
  @EventListener
  //@Async - BEZ Async ten event b�dzie wykonany w GL�WNYM w�tku 
  public void doYourWork(MyEventNew2 event) throws InterruptedException {    
        System.out.println(Thread.currentThread().getId() + ": Wykonuj� event (2.1):" + event.stMessage);    
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getId() + ": Wykonuj� event (2.2):" + event.stMessage);    
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getId() + ": Wykonuj� event (2.3):" + event.stMessage);  
        
        // Testowo wykonanie eventu dodaje do list kolejny event
        MyEventNew myEvent11 = new MyEventNew(this, "To tylko test new 13.");
        aep.publishEvent(myEvent11);
  }
}
