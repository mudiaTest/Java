package hibernate02;

import java.lang.reflect.Executable;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NewTransactionWrapper {
	
	@Transactional
	public void inTrans(Runnable proc){
		proc.run();
	}
	
	@Transactional
	public <T> T inTrans(Supplier<T> proc){
		return proc.get();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void inNewTrans(Runnable proc){
		proc.run();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public <T> T inNewTrans(Supplier<T> proc){
		return proc.get();
	}
	
}
