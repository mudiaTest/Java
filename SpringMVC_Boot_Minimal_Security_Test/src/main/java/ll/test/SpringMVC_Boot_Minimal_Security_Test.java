package ll.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

/*
 * Wyłączenie SS, gdy używamy spring boot 
 */
//@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class SpringMVC_Boot_Minimal_Security_Test {

	public static void main(String[] args) {
		SpringApplication.run(SpringMVC_Boot_Minimal_Security_Test.class, args);
	}
}
