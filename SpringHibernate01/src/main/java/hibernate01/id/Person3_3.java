package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Person3_3 {
  
  @Id
    /*
     * SEQUENCE - bazuje na obiekcie sekwencji z bazy danych. 
     * Je�li sekwencje nie sa wspierane, to b�dzie u�ywana opcja 
     * 
     * Domy�lna sekwencja to wsp�dzielona hibernate_sequence (u�ywana te� np przez Auto)
     * @GeneratedValue(strategy = GenerationType.SEQUENCE)
     * 
     * W�asna definicja sekwencji wymaga @SequenceGenerator
     */
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE, 
        /*
         * nazwa generatora - mo�e odnosi� si� do @SequenceGenerator lub @TableGenerator
         */
      generator="seq33gen")
  @SequenceGenerator(
        /*
         * Nazwa sekwencji - odpowiada warto�ci pola @GeneratedValue(generator=...) 
         */
      name="seq33gen", 
        /*
         * Nazwa obektu sekwencji w DB
         */
      sequenceName = "seq33DBgen", 
        /*
         * Pocz�tkowa warto�� sekwencji
         */
      initialValue = 4)
  public Long key1;
  public String name;    
}
