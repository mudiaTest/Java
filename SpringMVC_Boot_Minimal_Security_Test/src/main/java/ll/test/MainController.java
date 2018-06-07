package ll.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest ważne!
 */

@Controller
public class MainController {

	@RequestMapping("/home")
	public String home() {
		System.out.println("home(...)");
		return "home";
	}
	
	@RequestMapping("/hello")
	public String hej2() {
		System.out.println("hello(...)");
		return "hello";
	}
	
	//Nie zadziała  - resolver chyba nie rozpoznale tego jako template'u
	@RequestMapping("/img3")
	public String img3() {
		System.out.println("img3(...)");
		return "img3.jpg";
	}
	
}
