package ll.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {

	@RequestMapping("/hej1")
	public String hej1() {
		System.out.println("hej1(...)");
		return "hej1";
	}
	
}
