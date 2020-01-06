/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class MiscTest {

	public static <E> void addAll(Collection<? extends E> c) {
		c.add(null);
	}

	public static Object[] badCopyOf(Object[] array, int newLength) { // Not useful
		Object[] newArray = new Object[newLength];
		for (int i = 0; i < Math.min(array.length, newLength); i++)
			newArray[i] = array[i];
		return newArray;
	}

	public static Object goodCopyOf(Object array, int newLength) {
		// Pobieramy obiekt Class
		Class<?> cl = array.getClass();
		if (!cl.isArray())
			return null;
		// Pobieramy klasę obiektów w Array
		Class<?> componentType = cl.getComponentType();
		// Pobieramy wielkość tablicy
		int length = Array.getLength(array);
		// Tworzymy nowy obiekt tablicy o zadanej długości i type przechowywantch
		// obiektów
		Object newArray = Array.newInstance(componentType, newLength);
		// Wypełniamy nową tablicę referencjami pobranymi ze starej tablicy
		for (int i = 0; i < Math.min(length, newLength); i++)
			Array.set(newArray, i, Array.get(array, i));
		return newArray;
	}

	public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
	    InvocationTargetException, IOException, Exception, Throwable {
		CreateList();
		StringCompare();
		SortList();
		BasicListOper();
		CreateObjInStream();
		ObjectStreamToArray();
		ArrayToList();
		TestInterFunc();
		ZasiegZmiennej();

		CustomCompareOneVar();
		CustomCompareMultiVars();

		AnnonymousClass();

		ClassInfo();

		ArrCopy();

		ClassResource();
		AutoCloseResource();

		ExtendExcept();
		ExtendExceptInitCause();

		StackTrace();

		Assert();

		Logger();

		IteratorFillList();
	}

	// I/O - kopiowanie OS do IS
	public static void copy(InputStream in, OutputStream out) throws IOException {
		final int BLOCKSIZE = 1024;
		byte[] bytes = new byte[BLOCKSIZE];
		int len;
		while ((len = in.read(bytes)) != -1)// odczytuje kolejne porcje wielkości BLOCKSIZE
			out.write(bytes, 0, len); // zapisueje do stream całą tablicę (od 0 do len)
	}

	// I/O - odczytanie wszystkich bajtów z pliku
	public static byte[] readAllBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(in, out);
		out.close();
		return out.toByteArray();
	}

	// Tworzenie i wyświetlanie list
	// System.out.pront używa metody toString()
	public static void CreateList() {
		ArrayList<String> arr1 = new ArrayList<>(Arrays.asList("Ala, Igor"));
		ArrayList<Object> arr2 = new ArrayList<>(Arrays.asList(new Cont()));
		System.out.println("Lista stringów " + arr1);
		System.out.println("Lista obiektów " + arr2);
	}

	// Porównywanie stringów
	public static void StringCompare() {
		String a = "World";
		String greeting = "Hello, World!";
		String location = greeting.substring(7, 12);
		// == porównuje referencje więc to da błąd
		System.out.println("Błędne porówanie stringów '==': " + (location == "World"));
		// a to true, bo oba stringi są 'systemowe'
		System.out.println("Błędne porówanie stringów '==': " + ("World" == "World"));
		System.out.println("Poprawne porówanie stringów 'equals()': " + (location.equals("World")));
	}

	// Sotrowanie tablic
	public static void SortList() {
		String[] arr = { "ola", "ala", "Ala" };
		System.out.println("Pierwszy element listy (przed sortowaniem) " + arr[0]);

		Arrays.sort(arr);
		System.out.println("Pierwszy element listy (domyślne sortowanie) " + arr[0]);

		String[] arr2 = { "ola", "ala", "Ala" };
		Arrays.sort(arr2, String::compareToIgnoreCase);
		System.out.println("Pierwszy element listy (domyślne sortowanie + ignoreCase) " + arr2[0]);
	}

	// postawowe operacje na listach; usuwanie pod warunkiem i forEach
	public static void BasicListOper() {
		ArrayList<Integer> lst2 = new ArrayList<>();
		lst2.add(1);
		lst2.add(null);
		lst2.add(2);
		System.out.print("Lista przed remove: " + lst2);
		lst2.removeIf(e -> Objects.isNull(e));
		// ForEach - pętla po wszystkich elementach
		System.out.print("\nList.forEach(1) ");
		lst2.forEach(System.out::print);
		System.out.print("\nList.forEach(2) ");
		lst2.forEach(e -> System.out.print(" '" + e + "'"));
		System.out.println("");
	}

	//
	public static void CreateObjInStream() {
		List<String> names = new ArrayList<>(Arrays.asList("ola", "ala", "ala"));

		Stream<Employee> employesStream1 = names.stream().map(name -> new Employee(name));
		System.out.println("'employesStream1'");
		employesStream1.forEach(System.out::println);

		// poniższe działa tak samo. In to lista stringów i szuka konstruktora z
		// przyjmującego jeden string
		// 'name -> new Employee(name)' równoznaczne 'Employee::new'
		Stream<Employee> employesStream2 = names.stream().map(Employee::new);
		System.out.println("'employesStream2'");
		employesStream2.forEach(System.out::println);
	}

	// Metody technicze
	public static Stream<Employee> CreateEmplStream() {
		AtomicInteger counter = new AtomicInteger();
		List<String> names = new ArrayList<>(Arrays.asList("ola", "ala", "ala"));
		return names.stream().map(name -> new Employee(name, counter.getAndIncrement(), String.valueOf(counter.get())));
	}

	public static Employee[] CreateEmplArray() {
		return CreateEmplStream().toArray(Employee[]::new);
	}

	public static List<Employee> CreateEmplList() {
		return new ArrayList<>(Arrays.asList(CreateEmplArray()));
	}

	// Tworzenie tablicy obiektów ze streama tych obiektów.
	// Obiekty w tablicy so TE SAME obiekty, które są w streamie
	public static void ObjectStreamToArray() {
		// oba poniższe tworzą Array obiektów Employee o rozmiarze = ilości
		// obiektów w stream
		Employee[] emplArr1 = CreateEmplStream().toArray(Employee[]::new);
		Employee[] emplArr2 = CreateEmplStream().toArray(size -> new Employee[size]);
	}

	// Tworzenie listy obiektów z tablicy tych obiektów.
	// Obiekty w liście so TE SAME obiekty, które są w tablicy
	public static void ArrayToList() {
		// poniższe odda nową listę z oryginalnymi elementami Employee pobranymi z
		// Array, którą można rozszerzać
		List<Employee> emplLst1 = new ArrayList<>(Arrays.asList(CreateEmplArray()));
		// poniższe odda listę z oryginalnymi w elementami Employee pobranymi z Array,
		// która jest defacto opakowaniem dla array, NIE MOĹ»NA jej rozszerzać
		List<Employee> emplLst2 = Arrays.asList(CreateEmplArray());
	}

	// Test włanej funkcji z parametrem IF (interfejs funkcjyjny), czyli
	// przyjmującej LA (lambdę)
	public static void TestInterFunc() {
		Employee emplIza = new Employee("iza", 1);
		emplIza.Changer(emp -> emp.age += 2);
	}

	// Zasięg zmiennej i przechowywanie klas dla typow prostych w kontenerach i
	// tablicach
	public static void ZasiegZmiennej() {
		// to nie zadziała (bład kompilacji!), bo WL musi móc zachomikować wartość
		// i, a zniknie po zakoĹ„czeniu iteracji pętli
		// for (int i=0; i < 4; i++){
		// new Thread( ()->System.out.println(i) );
		// }

		// to zadziała, bo choć warości jest wiele, ale każda jest przechowywana we
		// włanej "zmiannej" w tablicy
		int lp1 = 0;
		Thread[] threadArr = new Thread[4];
		int[] loopArr = { 0, 1, 2, 3 };
		// UWAGA!
		for (int i : loopArr) {
			threadArr[lp1] = new Thread(() -> System.out.print(i + ", "));
			lp1++;
		}
		// Zmiana wartości nie będzie zanotowana przez Thread
		loopArr[1] = 55;
		System.out.print("Wyniki z Treadów (int): ");
		for (Thread t : threadArr) {
			t.run();
		}
		System.out.print("\n");

		// UWAGA !!! Tablica Integer[] przechowuje WARTOĹšCI a NIE REFERENCJE do
		// obiektów!
		// UWAGA !!! List<Integer> TEĹ» przechowuje WARTOĹšCI a NIE REFERENCJE do
		// obiektów!
		// UWAGA !!! Prawdopodobnie każdy kontener będzie tak działał z instancjami
		// klas typów prostych!
		int lp2 = 0;
		Thread[] threadArr2 = new Thread[4];
		Integer[] loopArr2 = new Integer[4];
		Integer o = new Integer(1);
		;
		loopArr2[0] = new Integer(0);
		loopArr2[1] = new Integer(1);
		loopArr2[2] = new Integer(2);
		loopArr2[3] = new Integer(3);
		System.out.println("---->" + loopArr2[1].hashCode());
		System.out.println("---->" + loopArr2[2].hashCode());
		for (Integer i : loopArr2) {
			System.out.println("--->" + (Integer) i.hashCode());
			threadArr2[lp2] = new Thread(() -> System.out.print(i.hashCode() + "|" + i.toString() + ", "));
			lp2++;
		}
		// Zmiana wartości NIE BÄ�DZIE zanotowana przez Thread
		loopArr2[1] = 55;
		System.out.println("-->" + loopArr2[1].hashCode());
		System.out.print("Wyniki z Treadów (Integer): ");
		for (Thread t : threadArr2) {
			t.run();
		}
		System.out.print("\n");

		// Przekazywanie Źródłowych obiektów
		int lp3 = 0;
		Thread[] threadArr3 = new Thread[4];
		IntWrap[] loopArr3 = new IntWrap[4];
		loopArr3[0] = new IntWrap(0);
		loopArr3[1] = new IntWrap(1);
		loopArr3[2] = new IntWrap(2);
		loopArr3[3] = new IntWrap(3);
		for (IntWrap i : loopArr3) {
			threadArr3[lp3] = new Thread(() -> System.out.print(i.hashCode() + "|" + i.value + ", "));
			lp3++;
		}
		// Zmiana wartości BÄ�DZIE zanotowana przez Thread
		loopArr3[1].value = 55;
		System.out.println("-->" + loopArr3[1].hashCode());
		System.out.print("Wyniki z Treadów (IntWrap/for): ");
		for (Thread t : threadArr3) {
			t.run();
		}
		System.out.print("\n");
	}

	// Porównywanie po jednej zmiannej
	public static void CustomCompareOneVar() {
		Employee[] emplArr = CreateEmplArray();
		System.out.println(Arrays.toString(emplArr));
		// porównywanie z wykorzystaniem domyślnego Comparator.comparing
		Arrays.sort(emplArr, Comparator.comparing(Employee::getName));
		// własne porównywanie (nie po literach i długości, ale tylko po długości)
		Arrays.sort(emplArr, Comparator.comparing(Employee::getName, (e1, e2) -> e1.length() - e2.length()));
		System.out.println(Arrays.toString(emplArr));
	}

	// Porównywanie po wielu zmiannych
	// Na wyniku porównania dokonujemy kolejnych porównaĹ„
	public static void CustomCompareMultiVars() {
		Employee[] emplArr = CreateEmplArray();
		emplArr[0].age = 1;
		System.out.println(Arrays.toString(emplArr));
		Arrays.sort(emplArr,
		    Comparator.comparing(Employee::getName).thenComparing(Employee::getAge).thenComparing(Employee::getAge));
		System.out.println(Arrays.toString(emplArr));
	}

	// Klasa anonimowa - klasa tworzona "w locie" - nadpisuje funkcję dodając jej
	// opcję logowania
	public static void AnnonymousClass() {
		ArrayList<String> arrNewConTest = new ArrayList<String>(10) {
			private int counter;

			@Override
			public boolean add(String element) {
				counter++;
				System.out.printf("Adding %s (%d)\n", element, counter);
				return super.add(element);
			}
		};
		arrNewConTest.add("pp");
		arrNewConTest.add("jeden");
		arrNewConTest.add("dwa");
	}

	//
	public static void ClassInfo() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
		List<Employee> emplLst2 = CreateEmplList();
		Class<?> cl = emplLst2.getClass();

		// Nazwa klasy
		// Cannonical name- czyli jak pokazać normalną informację o pewnych klasach
		System.out.println(String.format("Nazwa klasy: %s, %s", cl.getName(), cl.getCanonicalName()));

		// Class nie udostępnia metod danej klasy (nawet statycznych), ale informacje o
		// klasie
		Class<?> cl2 = StatiocCont.class;
		System.out.println(String.format("Nazwa klasy: %s, %s", cl2.getName(), cl2.getCanonicalName()));
		Method[] methods = cl2.getDeclaredMethods();
		System.out.println(String.format("Metody: %s", Arrays.toString(methods)));
		System.out.println("Nazwa metody: " + methods[0].getName());

		// Pobieranie informacji o metodzie private/public etc
		int modifiers = methods[1].getModifiers(); // public static void
		System.out.println("Metoda '" + methods[0].getName() + "' modyfikatory: " + Integer.toBinaryString(modifiers));
		System.out.println("Metoda '" + methods[0].getName() + "' static (T/N): " + Modifier.isStatic(modifiers));

		// Pobieranie i modyfikacja wartości pola
		// f odpowiada polu danej klasy a nie obiektu, więc przy zmianie lub odczytaniu
		// trzeba podać instancję klasy
		// NoSuchFieldException, IllegalAccessException
		StatiocCont stc = new StatiocCont();
		stc.val = 9;
		System.out.println("Val przed zmianą: " + stc.val);
		Field f = stc.getClass().getField("val");
		// ustalenie czegoś
		// f.setAccessible(false);
		f.setInt(stc, 6);
		System.out.println("Val po zmianie: " + f.getInt(stc));

		// Wywoływanie metod statycznych i niestatycznych
		// InvocationTargetException
		int i = 0;
		System.out.println(String.format("Próba wywołania metody: %s; static: %s", methods[i].getName(),
		    Modifier.isStatic(methods[i].getModifiers())));
		methods[i].invoke(null, 7);
		i = 1;
		System.out.println(String.format("Próba wywołania metody: %s; static: %s", methods[i].getName(),
		    Modifier.isStatic(methods[i].getModifiers())));
		try {
			methods[i].invoke(null, 7);
		} catch (Exception e) {
			System.out.println("Nie udało się wywołać metody.");
		}

		// Dynamiczne tworzenie Array danego typu hardcoded
		Employee[] emplDynCreArr = (Employee[]) Array.newInstance(Employee.class, 3);
		// Dynamiczne tworzenie Array danego typu pobranego z innego obiektu
		Class<?> cpomponentType = emplDynCreArr.getClass().getComponentType();
		Employee[] emplDynCreArr2 = (Employee[]) Array.newInstance(cpomponentType, 3);

		/*
		 * Employee emplPrx = Proxy.newProxyInstance( null, Employee.(), // Lambda
		 * expression for invocation handler (Object proxy, Method m, Object[] margs) ->
		 * { System.out.println(value + â€ś.â€ť + m.getName() + Arrays.toString(margs));
		 * return m.invoke(value, margs); });
		 */

		// Proxies - czegoś to jednak nie rozumiem
		// https://dzone.com/articles/java-dynamic-proxy
		/*
		 * Employee emp = new Employee("Arek", 7); Object emplPrx =
		 * Proxy.newProxyInstance( null, emp.getClass().getInterfaces(), // Lambda
		 * expression for invocation handler (Object proxy, Method m, Object[] margs) ->
		 * { System.out.println(emp + "." + m.getName() + Arrays.toString(margs));
		 * return m.invoke(emp, margs); }); emp.PrintTest();
		 */
	}

	public static void ArrCopy() {
		Employee[] emplArr = CreateEmplArray();
		// Kopiowanie tablicy generycznej.
		// Kopiowanie współdzieli obiekty.
		Employee[] emplArr2 = (Employee[]) goodCopyOf(emplArr, 3);
		// Poniższe nie zadziała, bo utworzona tablica o obiektach innego typu
		// Niedozwolone jest: Employee[] b = (Employee[]) new Object[1];
		try {
			Employee[] emplArr3 = (Employee[]) badCopyOf(emplArr, 3);
		} catch (Exception e) {
			System.out.println("Nie udało się skopiować tablicy z użyciem 'badCopyOf'.");
		}
	}

	// Użycie Class do wczytanie resouces
	// Ważne! Resource musi być w odpowiednim katalogu
	public static void ClassResource() {
		InputStream stream1 = StatiocCont.class.getResourceAsStream("12.txt");
		InputStream stream2 = StatiocCont.class.getResourceAsStream("res/11.txt");
		InputStream stream = stream1;
		Scanner scanner = new Scanner(stream);
		// for(String line: scanner){
		// }
		do {
			System.out.println(scanner.nextLine());
		} while (scanner.hasNext());
	}

	// Autonatyczne zamykanie resources na koniec bloku try
	public static void AutoCloseResource() throws Exception {
		try (Scanner in = new Scanner(Paths.get("/usr/share/dict/words"));
		    PrintWriter out = new PrintWriter("output.txt")) {
		} catch (Exception e) {
			// ...
		} finally {
			// tu nastąpi samoczynna zamknięcie i zwolnienie resource
			// ...
		}

		// Można też użyć w takiej sytuacji kodu, który łapie potencjalne
		// Exception z kodu finally
		try {
			try (Scanner in = new Scanner(Paths.get("/usr/share/dict/words"));
			    PrintWriter out = new PrintWriter("output.txt")) {
			} finally {
				// ...
			}
		} catch (Exception e) {
			// ...
			throw e;
		}
	}

	// Prosta zmiana jednego Exceprion na inny
	public static void ExtendExcept() throws Exception {
		try {
			// Access the database
			throw new IOException();
		} catch (IOException ex) {
			throw new Exception("database error", ex);
		}
	}

	// Zmiana jednego Exceprion na inny z użyciem initCause
	// Pomimo rzycenia Throwable, wywołując ExtendExceptInitCause w bloku
	// try/catch możemy wyłapać Exception
	public static void ExtendExceptInitCause() throws Throwable {
		try {
			// Access the database
			throw new IOException();
		} catch (IOException ex) {
			Throwable ex2 = new Exception("database error");
			// Exception ex2 = new Exception("database error"); //też zadziała
			ex2.initCause(ex);
			throw ex2;
		}
	}

	// StackTrace - przychwytywanie i logowanie
	public static void StackTrace() throws FileNotFoundException {
		try {
			throw new Exception("TEST EXCEPTION!");
		} catch (Exception e) {
			// drukuje ładny stos w kolejnych liniach na konsolę
			e.printStackTrace();

			// drukuje ładny stos w kolejnych liniach do wskazanego sreamu (tutaj pliku)
			File file = new File("D:\\a.txt");
			PrintStream s = new PrintStream(file);
			e.printStackTrace(s);

			// pobiera w jedną linię stos
			System.out.println("--> \n" + Arrays.asList(e.getStackTrace()).toString());
		}

		// Poniższe w każym momencie (także poza błędami) pobiera stackTrace
		String st0 = Arrays.asList(Thread.currentThread().getStackTrace()).toString();
		System.out.println("---0>" + st0);

		// Wycinek
		String st1 = Arrays.asList(Thread.currentThread().getStackTrace()).subList(1, 3).toString(); // [od, do), więc
		                                                                                             // (1,3) odda wiersz 2
		                                                                                             // i 3
		System.out.println("---1>" + st1);

		// Stream
		Arrays.asList(Thread.currentThread().getStackTrace()).stream().forEach(el -> System.out
		    .println("---2>" + "(" + el.getLineNumber() + "): " + el.getFileName() + "~>" + el.getMethodName())); // [od,
		                                                                                                          // do),
		                                                                                                          // więc
		                                                                                                          // (1,3)
		                                                                                                          // odda
		                                                                                                          // wiersz
		                                                                                                          // 2 i 3
		// .forEach(cnsmr);
		// .toArray(ts);/
	}

	// Asercje są tylko gdy odpalimy z asercjami. Properties->Run->MV Options i
	// dodać "-ea"
	// Asercje są Error więc catch(Throwable t) ich NIE ZĹ�APIE. Nalezy użyć
	// catch (AssertionError e), ale... (patrz dalej)
	// Asercje inaczej niż w Delphi powinny z założenia stopować program. Wpp
	// używać wyjątków
	public static void Assert() {
		int ii = 4;
		assert ii == 3;
	}

	public static void Logger() throws IOException {
		// Najprostsze logowanie - globalne do System.err
		Logger.getGlobal().info("Logowanie najprostsze");

		// Logowanie ma kilka poziomow ważnośc: SEVERE, WARNING, INFO, CONFIG, FINE,
		// FINER, FINEST
		Logger logger = Logger.getLogger("MojTestowyLogger");
		// Logger będzie logował wszystko od FINEST w górę (czyli wszystko)
		logger.setLevel(Level.FINEST);
		logger.info("Logowanie z poziomem 'FINEST'.");

		// Do loggera można dodawać i usuwać handlery
		// dodatkowe logowanie do pliku
		FileHandler handler = new FileHandler("D:/a.txt");
		// ustalamy, że logowane do pliku będa wszystkie od CONFIG w górę
		handler.setLevel(Level.CONFIG);
		logger.addHandler(handler);

		// przykłady logowaĹ„
		logger.severe("--> severe" + Arrays.asList(Thread.currentThread().getStackTrace()).toString());
		logger.warning("--> warning");
		logger.info("--> info");
		logger.config("--> config");// nie pokaże się na konsoli
		logger.fine("--> fine");// nie pokaże się na konsoli
		// Ustalamy poziom logowania handlera konsoli handlera (konsola)
		// Nie mamy dostępu do zaszytego handlera, więc dodajemy nowy handler (wpisy
		// nie będą się dublować)
		Handler consHand = new ConsoleHandler();
		consHand.setLevel(Level.FINER);
		logger.addHandler(consHand);
		logger.finer("--> finer");// pokaże się
		logger.finest("--> finest");// nie pokaże się

		// logowanie rzucenia wyjątku wraz ze stosem jako FINER. Exception przerobiony
		// jest na rezrezentację XMLową
		logger.setLevel(Level.FINER);
		handler.setLevel(Level.FINER);
		consHand.setLevel(Level.FINER);
		IOException ex = new IOException("Cannot read configuration");
		logger.throwing("com.mycompany.mylib.Reader", "read", ex);
		throw ex;
	}

	// Zastosowanie iteratora do wypełniania listy w zadanej kolejności i jej
	// edycji
	// Iterator będzie służył do chodzenia po liście, co pozwala na jej
	// wypełnianie i modyfikację
	public static void IteratorFillList() {
		List<String> friends = new ArrayList<>();
		ListIterator<String> iter = friends.listIterator();

		// Dodanie elemetu
		iter.add("Fred"); // Fred |
		System.out.println(friends);

		// Dodanie
		iter.add("Wilma"); // Fred Wilma |
		System.out.println(friends);

		// Jeden do tyłu
		iter.previous(); // Fred | Wilma
		System.out.println(friends);

		// Wstawienie onwej wartości we wskazane miejsce
		iter.set("Barney"); // Fred | Barney
		System.out.println(friends);

		// Dodanie elemetu
		iter.add("BamBam"); // Fred BamBam | Barney
		System.out.println(friends);
	}

}
