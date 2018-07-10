package ll.test;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest ważne!
 */

@Controller
public class MainController {
	
	@RequestMapping("/role")
	public String role(HttpServletRequest request, Authentication authentication, SecurityContextHolderAwareRequestWrapper request2) {
		System.out.println("role(...)");
		
		/*
		 * 1) role są budowane z przedrostkiem "ROLE_"
		 * 2) HttpServletRequest do BŁĘDNĄ odpowied należy użyc SecurityContextHolderAwareRequestWrapper
		 * 
		 */
		
		//BŁĘDNE wyniki
		System.out.println("Is1 USSSER: " + request.isUserInRole("USSSER"));//mozna sprawdzi tylko na konkretną rolę, a nie jaka jest rola
		System.out.println("Is1 ROLE_USSSER: " + request.isUserInRole("ROLE_USSSER"));	
		System.out.println("Is1 ADMIN: " + request.isUserInRole("ADMIN"));	
		
		//POPRAWNE wyniki
		System.out.println("Is2 USSSER: " + request2.isUserInRole("USSSER"));//mozna sprawdzi tylko na konkretną rolę, a nie jaka jest rola
		System.out.println("Is2 ROLE_USSSER: " + request2.isUserInRole("ROLE_USSSER"));	
		System.out.println("Is2 ADMIN: " + request2.isUserInRole("ADMIN"));	
		
		//Lista wszystkich ról użytkownika
		System.out.println("Prawa:" + authentication.getAuthorities().stream()
				.map(r -> r.getAuthority())
				.collect(Collectors.joining(",")));		
		return "role";
	}

	@RequestMapping("/home")
	public String home() {
		System.out.println("home(...)");
		return "home";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		System.out.println("admin(...)");
		return "admin";
	}
	
	@RequestMapping("/user")
	public String user() {
		System.out.println("user(...)");
		return "user";
	}
	
	@RequestMapping("/hello")
	public String hej2() {
		System.out.println("hello(...)");
		return "hello";
	}
	
	@RequestMapping("/adm/1")
	public String adm1() {
		System.out.println("adm1");
		return "adm1";
	}
	
	@RequestMapping("/adm/1/2")
	public String adm12() {
		System.out.println("adm12");
		return "adm12";
	}
	
	//Nie zadziała  - resolver chyba nie rozpoznale tego jako template'u
	@RequestMapping("/img3")
	public String img3() {
		System.out.println("img3(...)");
		return "img3.jpg";
	}
	
}
