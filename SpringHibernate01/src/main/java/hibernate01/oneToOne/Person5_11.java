package hibernate01.oneToOne;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

/*
 * Unidirectional
 */

@Data
@Entity
public class Person5_11 {
  @Id
  @GeneratedValue
  public Integer id;
  public String name;
    /*
     * UNIDIRECTIONAL
     * Spowowduje powstanie dodatkowej kolumny z kluczem adresu
     * Spowoduje powstanie FK
     */
  @OneToOne(
        /*
         * Ustala customow� nazwe kolumny z kluczem FK
         */
      cascade=CascadeType.ALL,
        /*
         * Fetch Lazy zadzia�a dla UNIDIRECTIONAL
         */
      fetch=FetchType.LAZY)
  @JoinColumn(name = "addId")
  public Address5_11 address;
  
}
