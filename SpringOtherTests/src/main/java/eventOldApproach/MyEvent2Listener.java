package eventOldApproach;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * Obiekt nas�uchoje i je�li pojawi si� event do ob�u�enia, to co� z nim robi
 */
@Component
/*GENERYK nad konkretnym EVENTEM - ka�da taka klasa obs�u�y event, ale kolejno�� nie jest zadana*/
public class MyEvent2Listener implements ApplicationListener<MyEvent2> {

  @Override
  public void onApplicationEvent(MyEvent2 event) {
    try {
      System.out.println("Wykonuj� event2 (1):" + event.stMessage);    
      Thread.sleep(100);
      System.out.println("Wykonuj� event2 (2):" + event.stMessage);    
      Thread.sleep(100);
      System.out.println("Wykonuj� event2 (3):" + event.stMessage);    
    }
    catch (Exception e) {
      System.out.println("ERROR");
    }
  }

}
