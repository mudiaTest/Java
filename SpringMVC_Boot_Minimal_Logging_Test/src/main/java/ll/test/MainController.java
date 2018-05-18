package ll.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {	
	
	static Logger log = Logger.getLogger(SpringMvc_Boot_Minimal_TestApplication.class.getName());	

	@RequestMapping("/hej2")
	public String hej2() {
		System.out.println("hej2(...)");	
		log.debug("Log debug message z hej2");
		log.info("Log info message z hej2");
		log.error("Log error message z hej2");
		return "hej2";
	}
	
}
