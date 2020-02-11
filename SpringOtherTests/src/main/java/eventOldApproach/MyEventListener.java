package eventOldApproach;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * Obiekt nas�uchoje i je�li pojawi si� event do ob�u�enia, to co� z nim robi
 */
@Component
/*GENERYK nad konkretnym EVENTEM - ka�da taka klasa obs�u�y event, ale kolejno�� nie jest zadana*/
public class MyEventListener implements ApplicationListener<MyEvent> {

  @Override
  public void onApplicationEvent(MyEvent event) {
    try {
      System.out.println("Wykonuj� event (1):" + event.stMessage);    
      Thread.sleep(010);
      System.out.println("Wykonuj� event (2):" + event.stMessage);    
      Thread.sleep(100);
      System.out.println("Wykonuj� event (3):" + event.stMessage);    
    }
    catch (Exception e) {
      System.out.println("ERROR");
    }
  }

}
