package hibernate01.oneToOne;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Address5_13 {
  
  @Id
  @GeneratedValue
  Integer id;

  public String city;
  
    /*
     * Spowoduje powstanie FK i kolumny
     */
  @OneToOne(
        /*
         * Dla strony parenta LAZY dzia�a
         */
      fetch=FetchType.LAZY)
  public Person5_13 person;
  
  public Address5_13(String city, String street) {
    super();
    this.city = city;
  }

  public Address5_13() {
    super();
  }  
}
