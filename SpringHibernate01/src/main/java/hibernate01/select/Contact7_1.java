package hibernate01.select;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contact7_1 {

  public String envelope;
  
  public Contact7_1(String envelope) {
    super();
    this.envelope = envelope;
  }

  public Contact7_1() {
    super();
  }  
}
