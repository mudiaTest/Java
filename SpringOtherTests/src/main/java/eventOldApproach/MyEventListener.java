package eventOldApproach;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * Obiekt nas³uchoje i jeœli pojawi siê event do ob³u¿enia, to coœ z nim robi
 */
@Component
/*GENERYK nad konkretnym EVENTEM - ka¿da taka klasa obs³u¿y event, ale kolejnoœæ nie jest zadana*/
public class MyEventListener implements ApplicationListener<MyEvent> {

	@Override
	public void onApplicationEvent(MyEvent event) {
		try {
			System.out.println("Wykonujê event (1):" + event.stMessage);		
			Thread.sleep(010);
			System.out.println("Wykonujê event (2):" + event.stMessage);		
			Thread.sleep(100);
			System.out.println("Wykonujê event (3):" + event.stMessage);		
		}
		catch (Exception e) {
			System.out.println("ERROR");
		}
	}

}
