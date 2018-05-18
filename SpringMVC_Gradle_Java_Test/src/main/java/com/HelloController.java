package com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

   @RequestMapping("/")
   public String index() {
	  System.out.println("index()");
      return "index";
   }

   //@PostMapping("/hello")
   @RequestMapping("/hello")
   public String sayHello(@RequestParam("name") String name, Model model) {
	  System.out.println("sayHello(...)"); 
      model.addAttribute("name", name);
      return "hello";//oddaje nazwę widoku jsp 
   }
   
   @RequestMapping("/hej2")
   public ModelAndView hej2() {
	   System.out.println("hej2(...)"); 
       String message = "Hello World, Spring is here!";
       return new ModelAndView("hej2", "message", message); //oddaje cały widok jsp 
   }
   
   @RequestMapping("/hej22")
   public String hej22() {
	   System.out.println("hej22(...)"); 
       return "hej2"; //oddaje cały widok jsp 
   }
   
   @RequestMapping("/hello2")
   public String hello2() {
	   System.out.println("hello2(...)"); 
       String message = "Hello World, Spring is here!";
       return "hello2"; //wywali błąd, bo brak takiego widoku jsp
   }
   
   //Oddaje statyczną stronę. 
   //Konieczne jest dodanie "static/" (patrz addResourceHandlers) do ścieżki 
   @RequestMapping("/hej3s")
   public ModelAndView  hej3s() {
	   System.out.println("hej3s(...)"); 
       return new ModelAndView("repo/hej3.html"); 
   }
	   //Alternatywa dla powyższego. Oddaje tylko nazwe pliku
	   /*
	   @RequestMapping("/hej3s2")
	   public String  hej3s2() {
		   System.out.println("hej3s2(...)"); 
	       return "static/hej3.html"; 
	   }
	   */
   
   //Oddaje statyczny element - w tym przypadku grafikę, która wyświetlana jest przez przeglądarkę
   @RequestMapping("/img1")
   public ModelAndView  img1() {
	   System.out.println("img1(...)"); 
       return new ModelAndView("repo/img1.jpg"); 
   } 
   
   //używa wyrazęnia regularnego do wyciągnięcia fragmentu adresu
   //https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-uri-templates-regex
   @RequestMapping("/img{nr:\\d}")
   public ModelAndView img(@PathVariable("nr") String numer) {
	   System.out.println("img(...)" + numer.toString()); 
	   return new ModelAndView("repo/img"+numer.toString()+".gif");
   }
}
