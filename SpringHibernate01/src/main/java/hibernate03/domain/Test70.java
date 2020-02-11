package hibernate03.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test70 {
  @Id
  Long id;
  
  int val;
  
  //@Version
  int ver;

}
