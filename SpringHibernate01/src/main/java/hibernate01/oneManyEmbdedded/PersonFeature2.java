package hibernate01.oneManyEmbdedded;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
//@Entity
@Entity
public class PersonFeature2 {
  @Id
  @GeneratedValue
  Integer id;
  String name;
  String value;
  
  public PersonFeature2(String name, String value){
    this.name = name;
    this.value = value;
  }

  public PersonFeature2() {
    super();
  }
  
}
