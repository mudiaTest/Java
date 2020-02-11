package hibernate03.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class SubClass62 {
  @Id
  private Long id;
  private int subIntVal1;
  @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test6_id")
  private Test6 parent;
  
  public SubClass62() {}
  
  public SubClass62(long id, int subIntVal1, Test6 parent) {
    super();
    this.id = id;
    this.subIntVal1 = subIntVal1;
    this.parent = parent;
  }
  private String subStVal1;
}
