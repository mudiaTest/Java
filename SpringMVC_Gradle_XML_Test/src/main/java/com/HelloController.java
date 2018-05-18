package com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

   @RequestMapping("/")
   public String index() {
	  System.out.println("-> index()");
      return "index";
   }

   //@PostMapping("/hello")
   @RequestMapping("/hello")
   public String sayHello(@RequestParam("name") String name, Model model) {
	  System.out.println("-> sayHello(...)"); 
      model.addAttribute("name", name);
      return "hello";//oddaje nazwę widoku jsp 
   }
   
   @RequestMapping("/hello2")
   public ModelAndView hello2() {
	   System.out.println("-> hello2(...)"); 
       String message = "Hello World, Spring is here!";
       return new ModelAndView("hej2", "message", message); //oddaje cały widok jsp 
   }
   
   @RequestMapping("/hello3")
   public String hello3() {
	   System.out.println("-> hello3(...)"); 
       String message = "Hello World, Spring is here!";
       return "hello3"; //wywali błąd, bo brak takiego widoku jsp
   }
   
   
   //Dopasuje wywołanie /sth /sth6 /sth6/, ale nie /sth6/7
   @RequestMapping("/sth*")
   public String sth1() {
	   System.out.println("-> sth1(...)"); 
       String message = "Hello World, Spring is here!";
       return "hello3"; //wywali błąd, bo brak takiego widoku jsp
   }
  
   //Dopasuje wywołania /sth /sth6 /sth6/, ale nie /sth6/7, choc wg def. powinno
   @RequestMapping("/sth**")
   public String sth2() {
	   System.out.println("-> sth2(...)"); 
       String message = "Hello World, Spring is here!";
       return "hello3"; //wywali błąd, bo brak takiego widoku jsp
   }
   
   //Dopasuje wywołania /sth/ oraz /sth/6, ale nie /sth/6/7
   @RequestMapping("/sth/*")
   public String sth3() {
	   System.out.println("-> sth3(...)"); 
       String message = "Hello World, Spring is here!";
       return "hello3"; //wywali błąd, bo brak takiego widoku jsp
   }
   
	 //Dopasuje wywołania jak /sth/* ale także sth/7/8/.....
	 @RequestMapping("/sth/**")
	 public String sth4() {
		   System.out.println("-> sth4(...)"); 
	     String message = "Hello World, Spring is here!";
	     return "hello3"; //wywali błąd, bo brak takiego widoku jsp
	 }

 //Dopasuje wywołania /sth/ sth6/ sth/7 sth6/7, ale nie sth6/7/8
   @RequestMapping("/sth*/*")
   public String sth5() {
	   System.out.println("-> sth5(...)"); 
       String message = "Hello World, Spring is here!";
       return "hello3"; //wywali błąd, bo brak takiego widoku jsp
   }
   
	 //Dopasuje wywołania sth6/7/8...
	 @RequestMapping("/sth*/**")
	 public String sth6() {
		   System.out.println("-> sth6(...)"); 
		   String message = "Hello World, Spring is here!";
		   return "hello3"; //wywali błąd, bo brak takiego widoku jsp
	 }   
  
}
