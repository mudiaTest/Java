package ll.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {

	@RequestMapping("/hej2")
	public String hej2() {
		System.out.println("hej2(...)");
		return "hej2";
	}
	
}
