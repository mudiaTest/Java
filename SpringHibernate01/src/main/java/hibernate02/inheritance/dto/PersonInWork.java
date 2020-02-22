package hibernate02.inheritance.dto;

import javax.persistence.Entity;

@Entity
public class PersonInWork extends Person {


  public String stInWork;
  
  public PersonInWork(String name) {
    super(name);
  }
  
  public PersonInWork() {
    super();
  }
  
  

}
