package ja1;

import java.util.List;

//import java.lang.@NonNull String; // BĹ‚Ä…d! â€” nie moĹĽna odnotowaÄ‡ import
import org.junit.Test; // https://mvnrepository.com/artifact/junit/junit

//adnotacje dodawane do klas i parametrĂłw w klasach generycznych
@SuppressWarnings("unchecked")
class User</* @Immutable */ V> {

	public void DoSomething() {
		String str = new /* @Localized */ String(""); // adnotacja do wywoĹ‚ania konstruktora
	}

	@SuppressWarnings("unused")
	int intVal;
	List</* @NonNull */ String> users; // oznacza, ĹĽe na liĹ›cie nie ma stringĂłw
	String /* @NonNull */ [][] arr;

	private /* @NonNull */ String text;
	// Adnotacja odnoszaca siÄ™
	/* @Id */ private String userId; // Adnotacja odnoszÄ…ca siÄ™ do zmiennej
}

class MyClass { // zastosowanie localized do superclass
	public void DoSomething() {
		System.out.println("MyClass.DoSomething");
	}
}

class MyClass2 extends MyClass { // zastosowanie localized do superclass
	@Override // sprawdza czy metoda nadpisuje metodÄ™ z klasy dziedziczonej
	@Deprecated // IDE powinno daÄ‡ warninga jeĹ›li uĹĽyjemy, ale na razie tylko przekreĹ›la
	public void DoSomething() {
		super.DoSomething();
		System.out.println("MyClass2.DoSomething");
	}

	// PoniĹĽsze siÄ™ nie skompiluje, bo
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

	// PrzykĹ‚adowa odnotacja
	@Test(timeout = 10000) // Adnotacja dla junitTest z parametrem
	@SuppressWarnings(value = "unchecked") // moĹĽe byÄ‡ wiele adnotacji do jednego kodu
	// @Test == @Test(timeout=0L) - "0L" do wartoĹ›Ä‡ domyĹ›lna
	// @SuppressWarnings(value="unchecked") == @SuppressWarnings("unchecked") -
	// "value=" moĹĽna pomijaÄ‡
	// @BugReport(reportedBy={"Harry", "Fred"}) - wartoĹ›Ä‡ jest tablicÄ…
	public static void AnnotSample() {
		// ...
	}

	public static void AnnoDummyTest(/* @ReadOnly */ String str) {

	}

	public static void AnnotCompileTest() {
		// spowoduje, ĹĽe nie bÄ™dzie pojawiaĹ‚o siÄ™ ostrzeĹĽenie o nieuzyciu zmiennej
		// @SuppressWarnings - peĹ‚na lista wartoĹ›ci -
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