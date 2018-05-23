package ll.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {

	@RequestMapping("/home")
	public String home() {
		System.out.println("home(...)");
		return "home";
	}
	
	@RequestMapping("/home2")
	public String home2() {
		System.out.println("home2(...)");
		return "home2";
	}
	
	//Nie zadzia³a - nale¿y u¿y ChainViewResolver
	@RequestMapping("/img5")
	public String img5() {
		System.out.println("img5(...)");
		return "img5.jpg";
	}
	
}
