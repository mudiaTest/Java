package asyncJPA;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

	@Async
	@Query(""
			+ " SELECT u"
			+ " FROM User u"
			+ " WHERE u.id = ?1")
	public User getById(Integer id);
	
	@Query(""
			+ " SELECT u"
			+ " FROM User u")
	public List<User> getUsers();
	
	@Async
	@Query(""
			+ " SELECT u"
			+ " FROM User u")
	public CompletableFuture<List<User>> getUsersComp();
}
