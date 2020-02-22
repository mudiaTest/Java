package hibernate03.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl")
public class Tbl {
  @Id
  public Integer val;
}
