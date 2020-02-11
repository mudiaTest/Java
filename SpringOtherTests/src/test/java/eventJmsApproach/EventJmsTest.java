package eventJmsApproach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jms.core.JmsTemplate;

import eventNewApproach.MyEventNew;
import eventNewApproach.MyEventNew2;

@SpringBootTest
public class EventJmsTest {

  @Autowired
  private JmsTemplate jms;

  // Przesy�anie 
  @Test
  public void testSynchronousEvent() throws InterruptedException {    
    MyEventJms ev = new MyEventJms();
    ev.stMessage = "test";
    ev.setStMesss("test2");
    ev.setHiddenTxt("test3");
    ev.setStTxt("test4");
    
    System.out.println("->1");
    jms.convertAndSend("MyEventJmss", ev); // Wymaga customowego MessageConverter
//    jms.convertAndSend("MyEventJms", "test"); // Domy�lny konwerter
//    jms.send("MyEventJmsTxt", s -> s.createTextMessage("hello queue world")); // Wysy�a String
//    jms.send("MyEventJmsLong", s -> s.createObjectMessage(11L)); // Wysy�a Serializable 
//    jms.send("MyEventJmsLong", s -> s.createTextMessage("12")); // Zadzia�, bo automagia postara si� o konwersj� "12" -> 12
    
    System.out.println("->2");    
    //Program mo�e zako�czy� si� przed wykonaniem wszykich w�tk�w, dlatego celowo op�ziamy jego zako�czenie - rozwi�zanie tlko dla test�w 
    Thread.sleep(1000);
    System.out.println("->3");    
  }
}
