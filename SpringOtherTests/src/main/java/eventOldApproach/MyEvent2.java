package eventOldApproach;

import org.springframework.context.ApplicationEvent;

/*
 * Obiekt spe³nia rolê kontenera na dane
 */
public class MyEvent2 extends ApplicationEvent {

	public String stMessage;
	/*
	 * Klasa MUSI posadaæ konstruktor z przynajmniej "Object source"
	 */
	public MyEvent2(Object source, String message) {
		super(source);
		stMessage = message;
	}

}
