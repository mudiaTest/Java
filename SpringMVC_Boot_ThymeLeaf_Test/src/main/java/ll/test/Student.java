package ll.test;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Student {
	/* Musi by public, zeby mog³o byc wyswietlane przez stronê
	 * Jeœli jednak chcemy pod³¹czy obiekt do formularza (patrz addStudent)	 
	 * to wymagane sa settery
	 */
	
	/*podstawy walidacji
	 * http://www.baeldung.com/javax-validation
	 */
	@NotEmpty(message = "Imiê jest wymagane")
	private String name;
	
	@NotNull
	private String gender;
	
	@Min(value = 20, message = "Student nie mo¿e by m³odszy niz 20 lat.")
	private Integer age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Student() {
	}
	
	public Student Student() {
		return this;
	}
	
	public Student setStudent(String name, String gender, Integer age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		return this;
	}
}
