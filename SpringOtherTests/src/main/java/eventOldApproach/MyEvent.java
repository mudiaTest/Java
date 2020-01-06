package eventOldApproach;

import org.springframework.context.ApplicationEvent;

/*
 * Obiekt spe³nia rolê kontenera na dane
 */
public class MyEvent extends ApplicationEvent {

	public String stMessage;
	/*
	 * Klasa MUSI posadaæ konstruktor z przynajmniej "Object source"
	 */
	public MyEvent(Object source, String message) {
		super(source);
		stMessage = message;
	}

}
