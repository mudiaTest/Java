package hibernate02.inheritance.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Person extends Person_gen{

  private String name;

  private String st;
  
  public Person(String name) {
    super();
    this.name = name;
  }
  
  public Person() {
    
  }
  
}
