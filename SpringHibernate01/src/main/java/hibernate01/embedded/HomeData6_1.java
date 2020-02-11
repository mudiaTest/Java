package hibernate01.embedded;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Embeddable
public class HomeData6_1 {

  public Address6_1 addr;
    /*
     * Domy�lnie b�dzie EAGER
     */
  @OneToOne(cascade=CascadeType.ALL)
  public Person6_1 owner;
    /*
     * Domy�lnie b�dzie LAZY
     */
  @OneToMany(cascade=CascadeType.ALL)
  public Set<Person6_1> roomMates;
  
  
  public HomeData6_1(Address6_1 addr, Person6_1 owner, Set<Person6_1> roomMates) {
    super();
    this.addr = addr;
    this.owner = owner;
    this.roomMates= roomMates;
  }

  public HomeData6_1() {
    super();
  }  
}
