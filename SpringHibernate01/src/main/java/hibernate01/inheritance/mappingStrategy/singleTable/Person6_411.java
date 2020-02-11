package hibernate01.inheritance.mappingStrategy.singleTable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import hibernate01.inheritance.Address6_2;
import lombok.Data;

@Data
@Entity
  /*
   * Dyscrminator column zadzia�a tylko dla najbardziej generycznej klasy
   */
//@DiscriminatorColumn(name="discCol11", discriminatorType=DiscriminatorType.STRING, length=20)
  /*
   * Warto�� wpisywana do discriminatorColumn
   * Domy�lnie dla STRING jest to nazwa tabeli a dla INTEGER jakie� liczby
   * 
   * Warto�� musi by� zgodna z typem discriminatorType inaczej da exception
   */
@DiscriminatorValue(value="1")
public class Person6_411 extends Person6_41Gen{
  public Address6_2 addr;
}
