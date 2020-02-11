package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.NamedQuery;

@Entity
public class Person3_1 {
  
  @Id
  /*
   * - @GeneratedValue z DOMY�LNYMI WARTO�CIAMI (strategy=AUTO) w Hibernate pozwala na prac� z klasami :
   *   Long, Integer, Short, BigInteger, BigDecimal i dodatkowo z java.util.UUID. 
   *   Na pewno nie dzia�a ze String, Float
   * - Warto�ci b�d� pobierane z domy�lnej i wp�dzielonej sekwencji HIBERNATE_SEQUENCE 
   *   lub (gdy baza nie wspiera sekwencji) z TableGenetator
   */
  @GeneratedValue
  public Long key1;
  public String name;    
}
