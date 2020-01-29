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

	// Przesy³anie 
	@Test
	public void testSynchronousEvent() throws InterruptedException {		
		MyEventJms ev = new MyEventJms();
		ev.stMessage = "test";
		ev.setStMesss("test2");
		ev.setHiddenTxt("test3");
		ev.setStTxt("test4");
		
		System.out.println("->1");
		jms.convertAndSend("MyEventJmss", ev); // Wymaga customowego MessageConverter
//		jms.convertAndSend("MyEventJms", "test"); // Domyœlny konwerter
//		jms.send("MyEventJmsTxt", s -> s.createTextMessage("hello queue world")); // Wysy³a String
//		jms.send("MyEventJmsLong", s -> s.createObjectMessage(11L)); // Wysy³a Serializable 
//		jms.send("MyEventJmsLong", s -> s.createTextMessage("12")); // Zadzia³, bo automagia postara siê o konwersjê "12" -> 12
		
		System.out.println("->2");		
	  //Program mo¿e zakoñczyæ siê przed wykonaniem wszykich w¹tków, dlatego celowo opóziamy jego zakoñczenie - rozwi¹zanie tlko dla testów 
    Thread.sleep(1000);
    System.out.println("->3");		
	}
}
