package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
/*
 * Definiuje jakby logiczne opakowanie, kt�re pozwala na obs�ug�.
 * Oznacza to, �e klucz b�dzie przechowywany w tabeli Person4_11
 * , a nie w jakiej� osobnej strukturze
 */
@IdClass(Person4Id.class)
public class Person4_11 {
  
    /*
     * Pola oznaczone @Id musz� zgadza� si� co do ilo�ci, nazwy i typu z tymi zawartymi w Person4Id     
     * 
     * @GeneratedValue dzia�a tak jak zwykle, ale nal�zy pami�tac, �e Typ z obiektu musi pokrywa� 
     * si� z typem pola z klasy klucza 
     * 
     * do klucza odwo�ujemy si� poprzez Person4_11.key1
     */
  @Id
    /*
     * @GeneratedValue przes�oni warto�c podstawion� do key1
     */
  @GeneratedValue
  public Integer key1;
  @Id
  public String key2;
  public String name;    
}
