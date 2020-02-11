package hibernate01.id;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
  /*
   * Definiuje jakby logiczne opakowanie, kt�re pozwala na obs�ug�.
   * Oznacza to, �e klucz b�dzie przechowywany w tabeli Person4_11
   * , a nie w jakiej� osobnej strukturze
   */
public class Person4_12 {
  
  /*
   * @EmbeddedId wymaga @Embeddable po stronie Klasy klucza
   * 
   * Kluczy nie mo�na generowa� @GeneratedValue.
   * 
   * Do klucza odwo�ujemy si� poprzez Person4_11.key.key1
   */
  @EmbeddedId
  public Person4Id key;
  public String name;    
}
