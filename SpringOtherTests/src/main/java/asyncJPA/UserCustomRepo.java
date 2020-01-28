package asyncJPA;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

public interface UserCustomRepo {
	@Async
	public CompletableFuture<List<Object>> getUsersCustomComp();
}
