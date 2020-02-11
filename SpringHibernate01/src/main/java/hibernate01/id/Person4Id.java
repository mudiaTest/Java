package hibernate01.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

  /*
   * @Embeddable jest konieczne je�li u�ywamy @EmbeddedId
   * U�ywaj�c @IdClass nie jest konieczne
   * Poza tym klasa wygl�da tak samo w obu przypadkach
   */
@Embeddable
public class Person4Id implements Serializable {
  public Integer key1;
  public String key2;
  
  public Person4Id(Integer key1, String key2) {
    super();
    this.key1 = key1;
    this.key2 = key2;
  }
  
  public Person4Id() {
    super();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    else if(obj.getClass().isAssignableFrom(Person4Id.class))
    {
      if ( ((Person4Id)obj).key1 == key1 && ((Person4Id)obj).key2 == key2 )
        return true;
      else
        return false;
    }
    else
      return false;
  }
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
  
  
}
