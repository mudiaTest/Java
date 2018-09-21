package my.com.pl.Hibernate_03.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Test1GenDao<T, ID extends Serializable> extends CrudRepository<T, ID> {
	//@Query("SELECT a FROM #{#entityName} a")
	public List<T> getAll();
}
