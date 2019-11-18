package ja1;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Repeatable;

//import java.lang.@NonNull String; // Błąd! â€” nie można odnotować import
import org.junit.Test; // https://mvnrepository.com/artifact/junit/junit


@Documented
@Retention(RetentionPolicy.RUNTIME)
//Definiuje dla jakich elementów annotacja ma działać i być dostępna
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
@interface CustomAnnototation {
}

//----- Definicja custom annotation -------

@interface Schedules {
	Schedule[] value();
	}

@Repeatable(Schedules.class) 
@interface Schedule {
	String time() default "09:00";
	}

class RepeatableTest{
	@Schedules(
			{
				@Schedule(),//default 09:00 
				@Schedule(time = "23:00")
			}) 
	void scheduledAlarm() {			
	}
	//W Runtime Wykorzystanie annotacji odbywa się w klasie opisanej annotacją lub np na starcie programu - ogólnie przez RTTI
}

//------------

//adnotacje dodawane do klas i parametrów w klasach generycznych
@SuppressWarnings("unchecked")
class User</* @Immutable */ V> {

	public void DoSomething() {
		String str = new /* @Localized */ String(""); // adnotacja do wywołania konstruktora
	}

	@SuppressWarnings("unused")
	int intVal;
	List</* @NonNull */ String> users; // oznacza, że na liście nie ma stringów
	String /* @NonNull */ [][] arr;

	private /* @NonNull */ String text;
	// Adnotacja odnoszaca się
	/* @Id */ private String userId; // Adnotacja odnosząca się do zmiennej
}

class MyClass { // zastosowanie localized do superclass
	public void DoSomething() {
		System.out.println("MyClass.DoSomething");
	}
}

class MyClass2 extends MyClass { // zastosowanie localized do superclass
	@Override // sprawdza czy metoda nadpisuje metodę z klasy dziedziczonej
	@Deprecated // IDE powinno dać warninga jeśli użyjemy, ale na razie tylko przekreśla
	public void DoSomething() {
		super.DoSomething();
		System.out.println("MyClass2.DoSomething");
	}

	// Poniższe się nie skompiluje, bo
	/*
	 * @Override public void DoSomething2() {
	 * System.out.println("MyClass2.DoSomething2"); }
	 */
}

//NiedokoĹ„czone - nic nie daje
class MyClass3 { // zastosowanie localized do superclass
	@ToString(includeName = false)
	int intVar;
	String strVar;

	public MyClass3() {
		intVar = 2;
		strVar = "aaa";
	}
}

public class AnnotationsTest {

	public static void main(String[] args) {
		// AnnotSample();
		// AnnotCompileTest();
		AnnotToString();
	}

	// Przykładowa odnotacja
	@Test(timeout = 10000) // Adnotacja dla junitTest z parametrem
	@SuppressWarnings(value = "unchecked") // może być wiele adnotacji do jednego kodu
	// @Test == @Test(timeout=0L) - "0L" do wartość domyślna
	// @SuppressWarnings(value="unchecked") == @SuppressWarnings("unchecked") -
	// "value=" można pomijać
	// @BugReport(reportedBy={"Harry", "Fred"}) - wartość jest tablicą
	public static void AnnotSample() {
		// ...
	}

	public static void AnnoDummyTest(/* @ReadOnly */ String str) {

	}

	public static void AnnotCompileTest() {
		// spowoduje, że nie będzie pojawiało się ostrzeżenie o nieuzyciu zmiennej
		// @SuppressWarnings - pełna lista wartości -
		// https://stackoverflow.com/questions/2037326/all-suppresswarnings-values
		@SuppressWarnings("unused")
		int r = 0;

		MyClass2 obj = new MyClass2();
		obj.DoSomething();

		// inne:
		// @SafeVarargs
		// @Generated
	}

	public static void AnnotToString() {
		MyClass3 o3 = new MyClass3();
		System.out.println("MyClass3.ToString: " + o3.toString());
	}
}