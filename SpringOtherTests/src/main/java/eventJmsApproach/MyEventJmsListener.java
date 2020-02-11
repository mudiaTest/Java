package eventJmsApproach;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyEventJmsListener {
  @JmsListener(destination = "MyEventJmss")
  public void receiveMessage(MyEventJms event)
  {
    System.out.println("-> listener");
    log.warn("-> " + event.stMessage);
    log.warn("-> " + event.getStMess());
    log.warn("-> " + event.getHiddenTxt());
    log.warn("-> " + event.getStTxt());
    System.out.println("--> listener");
  }
  
  @JmsListener(destination = "MyEventJmsTxt")
  public void receiveTxtMessage(String txt)
  {
    System.out.println("-> receiveTxtMessage");
    log.warn("-> " + txt);
    System.out.println("--> receiveTxtMessage");
  }
  
  @JmsListener(destination = "MyEventJmsLong")
  public void receiveLongMessage(Long number)
  {
    System.out.println("-> receiveLongMessage");
    log.warn("-> " + number);
    System.out.println("--> receiveLongMessage");
  }
}
