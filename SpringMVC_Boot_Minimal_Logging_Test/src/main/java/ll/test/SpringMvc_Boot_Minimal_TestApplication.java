/*http://www.baeldung.com/java-logging-intro
 *
 *config log4j.properties i log4j.xml mog¹ by u¿ywane zamiennie 
 * 
 */

package ll.test;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMvc_Boot_Minimal_TestApplication {

	static Logger log = Logger.getLogger(SpringMvc_Boot_Minimal_TestApplication.class.getName());	
	
	public static void main(String[] args) {		
		SpringApplication.run(SpringMvc_Boot_Minimal_TestApplication.class, args);
	}
}
