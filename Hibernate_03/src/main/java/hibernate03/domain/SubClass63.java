package hibernate03.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class SubClass63 {
  @Id
  private Long id;
  private int subIntVal1;
  
  public SubClass63() {}
  
  public SubClass63(long id, int subIntVal1) {
    super();
    this.id = id;
    this.subIntVal1 = subIntVal1;
  }
  private String subStVal1;
}
