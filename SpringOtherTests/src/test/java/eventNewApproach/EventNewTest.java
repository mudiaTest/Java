package eventNewApproach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import eventNewApproach.MyEventNew;
import eventNewApproach.MyEventNew2;

@SpringBootTest
public class EventNewTest {

	@Autowired
  private ApplicationEventPublisher aep;

	@Test
	public void testSynchronousEvent() throws InterruptedException {		
		// Tworzymy Event
		MyEventNew myEvent11 = new MyEventNew(this, "To tylko test new 11.");
		MyEventNew myEvent12 = new MyEventNew(this, "To tylko test new 12.");
		MyEventNew2 myEvent21 = new MyEventNew2(this, "To tylko test new 21.");
		System.out.println("Test: " + Thread.currentThread().getId());
		// Rzucamy event "w powietrze" aby zosta³ obs³u¿ony przez klasy "implements ApplicationListener<MyEvent>"
    aep.publishEvent(myEvent11);
    aep.publishEvent(myEvent12);
    aep.publishEvent(myEvent21);
    
    //Program mo¿e zakoñczyæ siê przed wykonaniem wszykich w¹tków, dlatego celowo opóziamy jego zakoñczenie - rozwi¹zanie tlko dla testów 
    Thread.sleep(10000);
	}
}
