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
		/* inicjalizacja z klas� jest niezb�dna, je�li chcemy u�ywac
		 * <Loggers><Logger>... 
		 * Je�li zainicjalizujemy ze stringiem (jw) dzia�ac b�dzie tylko
		 * <Loggers><Root>...
		 */
		return LogManager.getLogger(LogConfig.class); 
	}
}
