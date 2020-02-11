package eventOldApproach;

import org.springframework.context.ApplicationEvent;

/*
 * Obiekt spe�nia rol� kontenera na dane
 */
public class MyEvent2 extends ApplicationEvent {

  public String stMessage;
  /*
   * Klasa MUSI posada� konstruktor z przynajmniej "Object source"
   */
  public MyEvent2(Object source, String message) {
    super(source);
    stMessage = message;
  }

}
