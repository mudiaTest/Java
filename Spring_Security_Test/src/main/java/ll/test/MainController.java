package ll.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping("Access_Denied")
	public String adm() {
		System.out.println("Access_Denied");
		return "Access_Denied";
	}
	
	@RequestMapping("signin")
	public String signin(@RequestParam("username") String username) {
		System.out.println("signin");
		return "signin";
	}
	
	@RequestMapping("customLogin")
	public String customLogin() {//@RequestParam("username") String username) {
		System.out.println("customLogin");
		return "customLogin";
	}
	
	@RequestMapping("customFailure")
	public String customFailure(HttpServletRequest request) {
		System.out.println("customFailure");
		String referrer = request.getHeader("Referer");
	    request.getSession().setAttribute("url_prior_login", referrer);
	    System.out.println(" -> powrót do:" + referrer);
		return "customFailure";
	}
	
	//Nie zadziała  - resolver chyba nie rozpoznale tego jako template'u
	@RequestMapping("/img3")
	public String img3() {
		System.out.println("img3(...)");
		return "img3.jpg";
	}
	
}
