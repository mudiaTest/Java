package hibernate01.inheritance.mappingStrategy.singleTable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

@Data
@Entity
  /*
   * InheritanceType.SINGLE_TABLE - ca�a hierarchia mapiwana do jednej tabeli zawieraj�cej sum� p�l
   * 
   * Tabela ma discriminator column, kt�ra wskazuje na klas� na ko�cy �ancuszka
   * 
   * SINGLE_TABLE to watro�c domy�lna
   */
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
  /*
   * Opisuje kolumn� discriminator
   */
@DiscriminatorColumn(
      /*
       * Nazwa kolumny
       */
    name="discCol", 
      /*
       * Typ kolumny
       * Warto�c pobierana jest z @DiscriminatorValue z ko�cowej klasy
       */
    discriminatorType=DiscriminatorType.INTEGER,
      /*
       * Liczba znakow kolumny - bedzie exception je�li b�dzie za malo znak�w
       * Dzia�a tylko dla STRING, pomijana dla reszty 
       * Domy�lnie 31
       */
    length=5)
public abstract class Person6_41Gen {
  @Id
  @GeneratedValue
  public Integer id;
  public String name;
}
