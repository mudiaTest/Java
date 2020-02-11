package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person3_2 {
  
  @Id
  /*
   * IDENTITY - bazuje na warto�ciach autoincrement w kolimnie id w bazie
   * 
   * Tworzone jest pole BIGSERIAL (niezale�nie od klasy pola @Id) z warto��i� domy�ln� - generuj�c� kolejne klucze
   * Bigserial - large autoincrementing integer (1 to 9223372036854775807)
   * Zak�ada te� sekwencj� person3_2_key1_seq pracuj�c� na rzecz autoincrement
   * 
   * Je�li pole bedzie String ,to odda string z liczby, ale co je�li pole b�dzie Integer a tam bedzie BIGSERIAL?
   * IDENTITY - uniemo�liwa batch uploades
   */
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public String key1;
  public String name;    
}
