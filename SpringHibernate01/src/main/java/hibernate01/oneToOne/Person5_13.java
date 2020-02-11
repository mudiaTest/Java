package hibernate01.oneToOne;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

/*
 * Unidirectional
 */

@Data
@Entity
public class Person5_13 {
  @Id
  @GeneratedValue
  public Integer id;
  public String name;
    /*
     * BIDIRECTIONAL
     * NIE SPOWOWDUJE powstanie dodatkowej kolumny z kluczem adresu, bo jest MappedId
     */
  @OneToOne(cascade=CascadeType.ALL,
        /*
         * Wskazuje na pole w obiekcie klasy Address5_13 
         */
      mappedBy="person",    
        /*
         * LAZY zostaniwe zignorowane i uzyte zostanie EAGER (sa na to haki)
         */
      fetch=FetchType.LAZY
      )
  public Address5_13 address;
  
}
