package ll.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {

	@RequestMapping("/home")
	public String hej2() {
		System.out.println("home(...)");
		return "home";
	}
	
	//Nie zadzia³a - nale¿y u¿y ChainViewResolver
	@RequestMapping("/img5")
	public String img5() {
		System.out.println("img5(...)");
		return "img5.jpg";
	}
	
}
