package eventOldApproach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import eventOldApproach.MyEvent;
import eventOldApproach.MyEvent2;

@SpringBootTest
public class EventOldTest {

  @Autowired
  private ApplicationEventPublisher aep;

  @Test
  public void testSynchronousEvent() throws InterruptedException {    
    // Tworzymy Event
    MyEvent myEvent11 = new MyEvent(this, "To tylko test old 11.");
    MyEvent myEvent12 = new MyEvent(this, "To tylko test old 12.");
    MyEvent2 myEvent21 = new MyEvent2(this, "To tylko test old 21.");
    // Rzucamy event "w powietrze" aby zosta� obs�u�ony przez klasy "implements ApplicationListener<MyEvent>"
    aep.publishEvent(myEvent11);
    aep.publishEvent(myEvent12);
    aep.publishEvent(myEvent21);
    
    //Program mo�e zako�czy� si� przed wykonaniem wszykich w�tk�w, dlatego celowo op�iamy jego zako�czenie - rozwi�zanie tlko dla test�w 
    Thread.sleep(10000);
  }
}
