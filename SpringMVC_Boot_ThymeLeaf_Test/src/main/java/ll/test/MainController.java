package ll.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa�ne!
 */

@Controller
public class MainController {
	
	@Autowired
	private Students students;
	
	private void dodajStudentowJesliNikogoNieMa() {
		if (students.size() == 0) {
			students.add((new Student()).setStudent("Ala", "K", 20));
			students.add((new Student()).setStudent("Jacek", "M", 25));
			students.add((new Student()).setStudent("Iza", "K", 30));
		}
	}
	
	@RequestMapping("/home")
	/*
	 * Model model odnosi si� do widoku, k�ty zostanie wy�wietlony
	 */
	public String home(Model model) {
		System.out.println("home(...)");
		
		/* Umo�liwie przekazanie obieku do formularza. 
		 * Automatycznie wywo�a ToString().
		 */
		model.addAttribute("serverTime", LocalDateTime.now());
		
		dodajStudentowJesliNikogoNieMa();
		
		/* Przekazujemy do formularza liste, po ktorej mozemy iterowac.
		 * NIE MIO�NA przekazywa do listy boiekt�w takich jak String czy List<String>. 
		 * Nale�y stworzy dla nich wrappery.
		 */
		model.addAttribute("students", students);
		
		return "home";
	}
	
	/*
	 * 1) Odpalamy addStudent, kt�ry ma formularz wskazuj�cy na saveStudent.
	 *    Ten formularz spowiduje wygenerowanie nowego obiektu studenta.
	 * 2) Save student pobiera dane z formularza (i potencjalne b��dy).
	 *    Dodaje obiekt studenta (z parametr�w) do listy i przekierowuje na 
	 *    stron� z lista student�w (lub z powrotem do formularza w przypadku b��d�w)
	 */
	
	/*zapis studenta - wersja uproszczona, bez kontroli b��d�w*/
	
	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute Student student, Model model) {
		students.add(student);
        model.addAttribute("students", students);
        /*
         *  linia zapobiegaj�ca ponownemu wys�aniu danych POST. 
         *  Gdyby u�yc return "/home" to �ciezka wywo�a� by�a by nast�puj�ca
         *  	8080:/addStudent -> 8080:/saveStudent (to defacto wy�wietla home, ale nazwa   
         *      w linii adresu jest save i od�wierzaj�c t� stron� ponownie przekazujemy dane POST)
         *  U�ywaj�c return "redirect:/home" �ciezka wywo�a� jest nast�puj�ca  
         *      8080:/addStudent -> 8080:/home baz pokazywania 8080:/saveStudent w linii adresu 
         *      i od�wierzaj�c stron� lub cofaj�c si� w histotrii nie ma mo�liwo�ci na ponowne 
         *      przekazania danych POST
         *      
         *  Warto�ci wy�wietlane przez home s� poprawne, bo w samym @RequestMapping("/home") 
         *  dodajemy odpowiednie warto�ci. saveStudent nie przekazuje �adnych warto�ci do home
         *  Odpowiednie przyk�ady na tak� akcj� s� w projekcie SpringMVC_Boot_Error_Test
         */
        return "redirect:/home"; 
	}
	
	@GetMapping("/addStudent")
	public String dodajStudenta(@ModelAttribute Student student) {
		return "addStudent";
	}
	
	/*zapis studenta - wersja roszerzona o kontrol� b��d�w*/
	/*
	 * Samo dodanie obs�ugi b��d�w powoduje przylepienie odpowiedniej informacji do 
	 * obiektu (nie samego oczywi�cie bo to POJO).
	 * BindingResult errors daje po prostu dost�p do tych danych w kodzie.
	 * Dost�p do b��d�w w formularzu jest niezalezny, co wida na przyk�adzie 
	 * addStudentCheck.html 
	 */
	@PostMapping("/saveStudentCheck")
	public String seveStudentCheck(@Valid @ModelAttribute Student student, BindingResult errors, Model model) {
		if (!errors.hasErrors()) {
            students.add(student);
            model.addAttribute("students", students);
        }
        return ((errors.hasErrors()) ? "addStudentCheck" : "home"); 
	}
	
	/*
	 * adnotacja @ModelAttribute Student student jest niezbedna, aby 
	 * w formularzu miec dost�p do p� obiektu
	 */
	@GetMapping("/addStudentCheck")
	public String dodajStudentaCheck(@ModelAttribute Student student) {
		return "addStudentCheck";
	}
}
