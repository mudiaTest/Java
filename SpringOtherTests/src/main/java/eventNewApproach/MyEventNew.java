package eventNewApproach;

import org.springframework.context.ApplicationEvent;

/*
 * Obiekt spe�nia rol� kontenera na dane
 */
public class MyEventNew extends ApplicationEvent {

  public String stMessage;
  /*
   * Klasa MUSI posada� konstruktor z przynajmniej "Object source"
   */
  public MyEventNew(Object source, String message) {
    super(source);
    stMessage = message;
  }

}
