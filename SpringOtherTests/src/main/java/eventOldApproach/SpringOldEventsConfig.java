package eventOldApproach;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/*
 * Ta konfiguracja nie jest konieczna, ale wykomentowany fragment pokazuje jak zrobiæ wykonywanie asynchroniczne
 * Problem z wykonywaniem asynchronicznym jest taki, ze program mo¿e zakoñczyæ siê przed wykonaniem wszyskich watków.
 * Dotyczy to zw³aszcza testów.
 */
//@Configuration
@EnableAsync
public class SpringOldEventsConfig {    
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationSynchrnusEventMulticaster() {
    	SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
    	
    	eventMulticaster.setTaskExecutor(new SyncTaskExecutor());
//      eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
    	return eventMulticaster;
    }
}
