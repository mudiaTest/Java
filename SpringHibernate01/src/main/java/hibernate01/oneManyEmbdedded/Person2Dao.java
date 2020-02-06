package hibernate01.oneManyEmbdedded;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Person2Dao extends CrudRepository<Person2, Integer>{

}
