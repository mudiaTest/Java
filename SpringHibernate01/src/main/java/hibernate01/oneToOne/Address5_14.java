package hibernate01.oneToOne;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Address5_14 {
  
    /*
     * Warto�ci id zostan� przepisane z person_id, wi�c //@GeneratedValue jest niepotrzebne
     */
  @Id
  //@GeneratedValue
  Integer id;

  public String city;
  
    /*
     * Spowoduje powstanie FK i kolumny CHYBA �E U�YJEMY @MapsId
     */
  @OneToOne(
        /*
         * Dla strony parenta LAZY dzia�a
         */
      fetch=FetchType.LAZY)
    /*
     * Ustawia, �e w SQL nie bedzie ju� kolumny id, ale Pk i FK bed� u�ywa�y person_id
     */
  @MapsId
  public Person5_14 person;
  
  public Address5_14(String city, String street) {
    super();
    this.city = city;
  }

  public Address5_14() {
    super();
  }  
}
