package ll.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

	@Bean
	public Logger getAppLogger() {
		
		//return LogManager.getLogger("TestAppLogger");		
		/* inicjalizacja z klas¹ jest niezbêdna, jeœli chcemy u¿ywac
		 * <Loggers><Logger>... 
		 * Jeœli zainicjalizujemy ze stringiem (jw) dzia³ac bêdzie tylko
		 * <Loggers><Root>...
		 */
		return LogManager.getLogger(LogConfig.class); 
	}
}
