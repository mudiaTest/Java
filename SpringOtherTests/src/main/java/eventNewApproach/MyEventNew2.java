package eventNewApproach;

import org.springframework.context.ApplicationEvent;

/*
 * Obiekt spe³nia rolê kontenera na dane
 */
public class MyEventNew2 extends ApplicationEvent {

	public String stMessage;
	/*
	 * Klasa MUSI posadaæ konstruktor z przynajmniej "Object source"
	 */
	public MyEventNew2(Object source, String message) {
		super(source);
		stMessage = message;
	}

}
