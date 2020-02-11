package hibernate06.dao;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hibernate06.domain.Person;

public class Person0601CustomDaoImpl implements Person0601CustomDao {

  @Autowired
  EntityManager em;
  
  // Pozwala pobrać aktualną sesję hibernate
  private Session getCurrent() {
    return em.unwrap(Session.class);
  }

  
  @Override
  public List<Person> getPersonList() {
    String st = "Select p From Person p";
    Query q = em.createQuery(st);
    @SuppressWarnings("unchecked")
    List<Person> res = q.getResultList(); 
    return res;
  }
  
  @Override
  public List<String> getNameList() {
    String st = "Select p.name From Person p";
    Query q = em.createQuery(st);
    @SuppressWarnings("unchecked")
    List<String> res = q.getResultList(); 
    return res;
  }

  // Stream UŻYWA scroll() I ScrollableResult Z JDBC i pozwala pobierać pojedyncze wiersze. 
  // Nie ma to wpywu ma samo zapytanie, więc nie można używać, do optymalizacji" jak ROW, ale
  // pozwala na pobieranie dużych danych po kawałku
  @Override
  public Stream<String> getNameStream() {
    String st = "Select p.name From Person p";
    Session ses = getCurrent();
    Stream<String> res = ses.createQuery(st, String.class).stream();
    return res;
  }
  
  @Override
  public Stream<Person> getPersonStream() {
    String st = "Select p From Person p";
    Session ses = getCurrent();
    Stream<Person> res = ses.createQuery(st, Person.class).stream();
    return res;
  }

}
