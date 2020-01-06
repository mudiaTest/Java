package eventOldApproach;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * Obiekt nas³uchoje i jeœli pojawi siê event do ob³u¿enia, to coœ z nim robi
 */
@Component
/*GENERYK nad konkretnym EVENTEM - ka¿da taka klasa obs³u¿y event, ale kolejnoœæ nie jest zadana*/
public class MyEvent2Listener implements ApplicationListener<MyEvent2> {

	@Override
	public void onApplicationEvent(MyEvent2 event) {
		try {
			System.out.println("Wykonujê event2 (1):" + event.stMessage);		
			Thread.sleep(100);
			System.out.println("Wykonujê event2 (2):" + event.stMessage);		
			Thread.sleep(100);
			System.out.println("Wykonujê event2 (3):" + event.stMessage);		
		}
		catch (Exception e) {
			System.out.println("ERROR");
		}
	}

}
