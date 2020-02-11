package hibernate01.select;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude="person")
public class JobApplication7_1 {
  @Id
  private Long id;
  
  @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//  @JoinColumn(name)
  private Person7_1 person;

  private String company;
  
  public JobApplication7_1(String envelope) {
    super();
    this.company = company;
  }

  public JobApplication7_1() {
    super();
  }  
}
