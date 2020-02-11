package eventOldApproach;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/*
 * Ta konfiguracja nie jest konieczna, ale wykomentowany fragment pokazuje jak zrobi� wykonywanie asynchroniczne
 * Problem z wykonywaniem asynchronicznym jest taki, ze program mo�e zako�czy� si� przed wykonaniem wszyskich watk�w.
 * Dotyczy to zw�aszcza test�w.
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
