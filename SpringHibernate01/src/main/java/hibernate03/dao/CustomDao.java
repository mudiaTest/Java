package hibernate03.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component("CustomDaoy")
public interface CustomDao {
  public int getCustomImplRef();
}
