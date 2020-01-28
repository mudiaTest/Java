package asyncJPA;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserCustomRepoImpl implements UserCustomRepo{

	@Autowired
	EntityManager em;
	
	@Override
	@Async
	public CompletableFuture<List<Object>> getUsersCustomComp() {
		Query q = em.createQuery(""
				+ " SELECT u"
				+ " FROM User u");
		log.info("getUsersCustomComp; before getResultList"); 
		List<Object> res = q.getResultList();
		log.info("getUsersCustomComp; after getResultList"); 
		return CompletableFuture.completedFuture(res);
	}

}
