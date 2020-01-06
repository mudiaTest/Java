package eventNewApproach;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
 * Obiekt nas³uchoje i jeœli pojawi siê event do ob³u¿enia, to coœ z nim robi
 */
@Component
public class MyEventNewListener  {
	
	@EventListener
	@Async // - ten event bêdzie wykonany w OSOBNYM w¹tku 
	public void doYourWork(MyEventNew event) throws InterruptedException {
		System.out.println(Thread.currentThread().getId() + ": Wykonujê event (1.1):" + event.stMessage);		
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getId() + ": Wykonujê event (1.2):" + event.stMessage);		
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getId() + ": Wykonujê event (1.3):" + event.stMessage);		
	}
}
