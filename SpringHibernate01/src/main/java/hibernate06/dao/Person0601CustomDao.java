package hibernate06.dao;

import java.util.List;
import java.util.stream.Stream;

import hibernate06.domain.Person;

public interface Person0601CustomDao {
  public List<String> getNameList();
  public List<Person> getPersonList();
  public Stream<String> getNameStream();
  public Stream<Person> getPersonStream();  
}
