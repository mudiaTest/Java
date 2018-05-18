/*
 * 1) Brak implement  implements WebMvcConfigurer {...} powoduje w�acznie autokonfiguracji daj�cej min resources
 * https://stackoverflow.com/questions/24661289/spring-boot-not-serving-static-content
 * 
 * Wi�cej o autokonfiguracji
 * https://www.logicbig.com/tutorials/spring-framework/spring-boot/boot-mvc-auto-configuration.html
 */

package ll.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMVC_Boot_Minimal_ThymeLeaf_Min_Test {

	public static void main(String[] args) {
		SpringApplication.run(SpringMVC_Boot_Minimal_ThymeLeaf_Min_Test.class, args);
	}
}
