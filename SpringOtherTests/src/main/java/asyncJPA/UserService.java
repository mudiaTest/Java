package asyncJPA;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepo ur;
	
	public List<User> getUsers() {
		
		System.out.println();
		log.info("->getUsers");
		List<User> res = ur.getUsers();
		log.info("-->getUsers");
		return res;
	}
	
	@Async
	public List<User> getUsersAsync() {
	
		System.out.println();
		log.info("->getUsersAsync");
		List<User> res = ur.getUsers();
		log.info("-->getUsersAsync");
		return res;
	}
	
	@Async
	public CompletableFuture<List<User>> getUsersAsyncCompl() {
		
		System.out.println();
		log.info("->getUsersAsyncCompl");
		List<User> res = ur.getUsers();
		log.info("-->getUsersAsyncCompl");
		return CompletableFuture.completedFuture(res);
	}
	
	@Async
	public List<User> getUsersComp() throws InterruptedException, ExecutionException {
		System.out.println();
		log.info("->getUsersComp");
		List<User> res = ur.getUsersComp().get();
		log.info("-->getUsersComp");
		return res;
	}
	
	@Async
	public CompletableFuture<List<User>> getUsersCompComp() throws InterruptedException, ExecutionException {
		System.out.println();
		log.info("->getUsersComp");
		List<User> res = ur.getUsersComp().get();
		log.info("-->getUsersComp");
		return CompletableFuture.completedFuture(res);
	}
	
	@Async
	public CompletableFuture<Void> delay(Integer delay) throws InterruptedException {
		Thread.sleep(delay);
		return null;
	}
}
