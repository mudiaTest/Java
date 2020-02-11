package eventNewApproach;

import org.springframework.context.ApplicationEvent;

/*
 * Obiekt spe�nia rol� kontenera na dane
 */
public class MyEventNew2 extends ApplicationEvent {

  public String stMessage;
  /*
   * Klasa MUSI posada� konstruktor z przynajmniej "Object source"
   */
  public MyEventNew2(Object source, String message) {
    super(source);
    stMessage = message;
  }

}
