package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class Person3_42 {
  
  @Id
  /*
   * TABLE - wykorzystuje tabele DB do przechowywania klucza 
   * Jedna tabeli mo�e by� wykorzystywana przez wiele klas
   * 
   */
  @GeneratedValue(
      strategy = GenerationType.TABLE, 
      generator="tab34gen")
  @TableGenerator(
      name="tab34gen", 
        /*
         * Opcjonalne - nazwa tabeli
         */
      table = "tab34Id",
        /*
         * Opcjoanlne - nazwa kolumny PK
         */
      pkColumnName = "id",
        /*
         * "kod" grupuj�cy klucze nale��ce do tej Person3_4, bo
         */
      pkColumnValue = "val42")
  public Long key1;
  public String name;    
}
