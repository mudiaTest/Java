package ll.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {	
	
	//Logger mo¿na autowireowa lub utworzy specjalnie dla danej klasy.
	//To drugie rozw¹zanie zapewnia lepsz¹ kontrolê nad <Loggers><Logger>
	@Autowired
	private Logger log;
	
	//private Logger log = LogManager.getLogger("TestAppLogger");

	@RequestMapping("/hej2")
	public String hej2() {
		System.out.println("hej2(...)");	
		log.debug("Log debug message z hej2");
		log.info("Log info message z hej2");
		log.error("Log error message z hej2");
		return "hej2";
	}
	
}
