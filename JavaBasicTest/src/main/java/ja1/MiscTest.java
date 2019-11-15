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
		// Pobieramy klasÄ™ obiektĂłw w Array
		Class<?> componentType = cl.getComponentType();
		// Pobieramy wielkoĹ›Ä‡ tablicy
		int length = Array.getLength(array);
		// Tworzymy nowy obiekt tablicy o zadanej dĹ‚ugoĹ›ci i type przechowywantch
		// obiektĂłw
		Object newArray = Array.newInstance(componentType, newLength);
		// WypeĹ‚niamy nowÄ… tablicÄ™ referencjami pobranymi ze starej tablicy
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
		while ((len = in.read(bytes)) != -1)// odczytuje kolejne porcje wielkoĹ›ci BLOCKSIZE
			out.write(bytes, 0, len); // zapisueje do stream caĹ‚Ä… tablicÄ™ (od 0 do len)
	}

	// I/O - odczytanie wszystkich bajtĂłw z pliku
	public static byte[] readAllBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(in, out);
		out.close();
		return out.toByteArray();
	}

	// Tworzenie i wyĹ›wietlanie list
	// System.out.pront uĹĽywa metody toString()
	public static void CreateList() {
		ArrayList<String> arr1 = new ArrayList<>(Arrays.asList("Ala, Igor"));
		ArrayList<Object> arr2 = new ArrayList<>(Arrays.asList(new Cont()));
		System.out.println("Lista stringĂłw " + arr1);
		System.out.println("Lista obiektĂłw " + arr2);
	}

	// PorĂłwnywanie stringĂłw
	public static void StringCompare() {
		String a = "World";
		String greeting = "Hello, World!";
		String location = greeting.substring(7, 12);
		// == porĂłwnuje referencje wiÄ™c to da bĹ‚Ä…d
		System.out.println("BĹ‚Ä™dne porĂłwanie stringĂłw '==': " + (location == "World"));
		// a to true, bo oba stringi sÄ… 'systemowe'
		System.out.println("BĹ‚Ä™dne porĂłwanie stringĂłw '==': " + ("World" == "World"));
		System.out.println("Poprawne porĂłwanie stringĂłw 'equals()': " + (location.equals("World")));
	}

	// Sotrowanie list
	public static void SortList() {
		String[] arr = { "ola", "ala", "Ala" };
		System.out.println("Pierwszy element listy (przed sortowaniem) " + arr[0]);

		Arrays.sort(arr);
		System.out.println("Pierwszy element listy (domyĹ›lne sortowanie) " + arr[0]);

		String[] arr2 = { "ola", "ala", "Ala" };
		Arrays.sort(arr2, String::compareToIgnoreCase);
		System.out.println("Pierwszy element listy (domyĹ›lne sortowanie + ignoreCase) " + arr2[0]);
	}

	// postawowe operacje na listach; usuwanie pod warunkiem i forEach
	public static void BasicListOper() {
		ArrayList<Integer> lst2 = new ArrayList<>();
		lst2.add(1);
		lst2.add(null);
		lst2.add(2);
		System.out.print("Lista przed remove: " + lst2);
		lst2.removeIf(e -> Objects.isNull(e));
		// ForEach - pÄ™tla po wszystkich elementach
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

		// poniĹĽsze dziaĹ‚a tak samo. In to lista stringĂłw i szuka konstruktora z
		// przyjmujÄ…cego jeden string
		// 'name -> new Employee(name)' rĂłwnoznaczne 'Employee::new'
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

	// Tworzenie tablicy obiektĂłw ze streama tych obiektĂłw.
	// Obiekty w tablicy so TE SAME obiekty, ktĂłre sÄ… w streamie
	public static void ObjectStreamToArray() {
		// oba poniĹĽsze tworzÄ… Array obiektĂłw Employee o rozmiarze = iloĹ›ci
		// obiektĂłw w stream
		Employee[] emplArr1 = CreateEmplStream().toArray(Employee[]::new);
		Employee[] emplArr2 = CreateEmplStream().toArray(size -> new Employee[size]);
	}

	// Tworzenie listy obiektĂłw z tablicy tych obiektĂłw.
	// Obiekty w liĹ›cie so TE SAME obiekty, ktĂłre sÄ… w tablicy
	public static void ArrayToList() {
		// poniĹĽsze odda nowÄ… listÄ™ z oryginalnymi elementami Employee pobranymi z
		// Array, ktĂłrÄ… moĹĽna rozszerzaÄ‡
		List<Employee> emplLst1 = new ArrayList<>(Arrays.asList(CreateEmplArray()));
		// poniĹĽsze odda listÄ™ z oryginalnymi w elementami Employee pobranymi z Array,
		// ktĂłra jest defacto opakowaniem dla array, NIE MOĹ»NA jej rozszerzaÄ‡
		List<Employee> emplLst2 = Arrays.asList(CreateEmplArray());
	}

	// Test wĹ‚anej funkcji z parametrem IF (interfejs funkcjyjny), czyli
	// przyjmujÄ…cej LA (lambdÄ™)
	public static void TestInterFunc() {
		Employee emplIza = new Employee("iza", 1);
		emplIza.Changer(emp -> emp.age += 2);
	}

	// ZasiÄ™g zmiennej i przechowywanie klas dla typow prostych w kontenerach i
	// tablicach
	public static void ZasiegZmiennej() {
		// to nie zadziaĹ‚a (bĹ‚ad kompilacji!), bo WL musi mĂłc zachomikowaÄ‡ wartoĹ›Ä‡
		// i, a zniknie po zakoĹ„czeniu iteracji pÄ™tli
		// for (int i=0; i < 4; i++){
		// new Thread( ()->System.out.println(i) );
		// }

		// to zadziaĹ‚a, bo choÄ‡ waroĹ›ci jest wiele, ale kaĹĽda jest przechowywana we
		// wĹ‚anej "zmiannej" w tablicy
		int lp1 = 0;
		Thread[] threadArr = new Thread[4];
		int[] loopArr = { 0, 1, 2, 3 };
		// UWAGA!
		for (int i : loopArr) {
			threadArr[lp1] = new Thread(() -> System.out.print(i + ", "));
			lp1++;
		}
		// Zmiana wartoĹ›ci nie bÄ™dzie zanotowana przez Thread
		loopArr[1] = 55;
		System.out.print("Wyniki z TreadĂłw (int): ");
		for (Thread t : threadArr) {
			t.run();
		}
		System.out.print("\n");

		// UWAGA !!! Tablica Integer[] przechowuje WARTOĹšCI a NIE REFERENCJE do
		// obiektĂłw!
		// UWAGA !!! List<Integer> TEĹ» przechowuje WARTOĹšCI a NIE REFERENCJE do
		// obiektĂłw!
		// UWAGA !!! Prawdopodobnie kaĹĽdy kontener bÄ™dzie tak dziaĹ‚aĹ‚ z instancjami
		// klas typĂłw prostych!
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
		// Zmiana wartoĹ›ci NIE BÄ�DZIE zanotowana przez Thread
		loopArr2[1] = 55;
		System.out.println("-->" + loopArr2[1].hashCode());
		System.out.print("Wyniki z TreadĂłw (Integer): ");
		for (Thread t : threadArr2) {
			t.run();
		}
		System.out.print("\n");

		// Przekazywanie ĹşrĂłdĹ‚owych obiektĂłw
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
		// Zmiana wartoĹ›ci BÄ�DZIE zanotowana przez Thread
		loopArr3[1].value = 55;
		System.out.println("-->" + loopArr3[1].hashCode());
		System.out.print("Wyniki z TreadĂłw (IntWrap/for): ");
		for (Thread t : threadArr3) {
			t.run();
		}
		System.out.print("\n");
	}

	// PorĂłwnywanie po jednej zmiannej
	public static void CustomCompareOneVar() {
		Employee[] emplArr = CreateEmplArray();
		System.out.println(Arrays.toString(emplArr));
		// porĂłwnywanie z wykorzystaniem domyĹ›lnego Comparator.comparing
		Arrays.sort(emplArr, Comparator.comparing(Employee::getName));
		// wĹ‚asne porĂłwnywanie (nie po literach i dĹ‚ugoĹ›ci, ale tylko po dĹ‚ugoĹ›ci)
		Arrays.sort(emplArr, Comparator.comparing(Employee::getName, (e1, e2) -> e1.length() - e2.length()));
		System.out.println(Arrays.toString(emplArr));
	}

	// PorĂłwnywanie po wielu zmiannych
	// Na wyniku porĂłwnania dokonujemy kolejnych porĂłwnaĹ„
	public static void CustomCompareMultiVars() {
		Employee[] emplArr = CreateEmplArray();
		emplArr[0].age = 1;
		System.out.println(Arrays.toString(emplArr));
		Arrays.sort(emplArr,
		    Comparator.comparing(Employee::getName).thenComparing(Employee::getAge).thenComparing(Employee::getAge));
		System.out.println(Arrays.toString(emplArr));
	}

	// Klasa anonimowa - klasa tworzona "w locie" - nadpisuje funkcjÄ™ dodajÄ…c jej
	// opcjÄ™ logowania
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
		// Cannonical name- czyli jak pokazaÄ‡ normalnÄ… informacjÄ™ o pewnych klasach
		System.out.println(String.format("Nazwa klasy: %s, %s", cl.getName(), cl.getCanonicalName()));

		// Class nie udostÄ™pnia metod danej klasy (nawet statycznych), ale informacje o
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

		// Pobieranie i modyfikacja wartoĹ›ci pola
		// f odpowiada polu danej klasy a nie obiektu, wiÄ™c przy zmianie lub odczytaniu
		// trzeba podaÄ‡ instancjÄ™ klasy
		// NoSuchFieldException, IllegalAccessException
		StatiocCont stc = new StatiocCont();
		stc.val = 9;
		System.out.println("Val przed zmianÄ…: " + stc.val);
		Field f = stc.getClass().getField("val");
		// ustalenie czegoĹ›
		// f.setAccessible(false);
		f.setInt(stc, 6);
		System.out.println("Val po zmianie: " + f.getInt(stc));

		// WywoĹ‚ywanie metod statycznych i niestatycznych
		// InvocationTargetException
		int i = 0;
		System.out.println(String.format("PrĂłba wywoĹ‚ania metody: %s; static: %s", methods[i].getName(),
		    Modifier.isStatic(methods[i].getModifiers())));
		methods[i].invoke(null, 7);
		i = 1;
		System.out.println(String.format("PrĂłba wywoĹ‚ania metody: %s; static: %s", methods[i].getName(),
		    Modifier.isStatic(methods[i].getModifiers())));
		try {
			methods[i].invoke(null, 7);
		} catch (Exception e) {
			System.out.println("Nie udaĹ‚o siÄ™ wywoĹ‚aÄ‡ metody.");
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

		// Proxies - czegoĹ› to jednak nie rozumiem
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
		// Kopiowanie wspĂłĹ‚dzieli obiekty.
		Employee[] emplArr2 = (Employee[]) goodCopyOf(emplArr, 3);
		// PoniĹĽsze nie zadziaĹ‚a, bo utworzona tablica o obiektach innego typu
		// Niedozwolone jest: Employee[] b = (Employee[]) new Object[1];
		try {
			Employee[] emplArr3 = (Employee[]) badCopyOf(emplArr, 3);
		} catch (Exception e) {
			System.out.println("Nie udaĹ‚o siÄ™ skopiowaÄ‡ tablicy z uĹĽyciem 'badCopyOf'.");
		}
	}

	// UĹĽycie Class do wczytanie resouces
	// WaĹĽne! Resource musi byÄ‡ w odpowiednim katalogu
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
			// tu nastÄ…pi samoczynna zamkniÄ™cie i zwolnienie resource
			// ...
		}

		// MoĹĽna teĹĽ uĹĽyÄ‡ w takiej sytuacji kodu, ktĂłry Ĺ‚apie potencjalne
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

	// Zmiana jednego Exceprion na inny z uĹĽyciem initCause
	// Pomimo rzycenia Throwable, wywoĹ‚ujÄ…c ExtendExceptInitCause w bloku
	// try/catch moĹĽemy wyĹ‚apaÄ‡ Exception
	public static void ExtendExceptInitCause() throws Throwable {
		try {
			// Access the database
			throw new IOException();
		} catch (IOException ex) {
			Throwable ex2 = new Exception("database error");
			// Exception ex2 = new Exception("database error"); //teĹĽ zadziaĹ‚a
			ex2.initCause(ex);
			throw ex2;
		}
	}

	// StackTrace - przychwytywanie i logowanie
	public static void StackTrace() throws FileNotFoundException {
		try {
			throw new Exception("TEST EXCEPTION!");
		} catch (Exception e) {
			// drukuje Ĺ‚adny stos w kolejnych liniach na konsolÄ™
			e.printStackTrace();

			// drukuje Ĺ‚adny stos w kolejnych liniach do wskazanego sreamu (tutaj pliku)
			File file = new File("D:\\a.txt");
			PrintStream s = new PrintStream(file);
			e.printStackTrace(s);

			// pobiera w jednÄ… liniÄ™ stos
			System.out.println("--> \n" + Arrays.asList(e.getStackTrace()).toString());
		}

		// PoniĹĽsze w kaĹĽym momencie (takĹĽe poza bĹ‚Ä™dami) pobiera stackTrace
		String st0 = Arrays.asList(Thread.currentThread().getStackTrace()).toString();
		System.out.println("---0>" + st0);

		// Wycinek
		String st1 = Arrays.asList(Thread.currentThread().getStackTrace()).subList(1, 3).toString(); // [od, do), wiÄ™c
		                                                                                             // (1,3) odda wiersz 2
		                                                                                             // i 3
		System.out.println("---1>" + st1);

		// Stream
		Arrays.asList(Thread.currentThread().getStackTrace()).stream().forEach(el -> System.out
		    .println("---2>" + "(" + el.getLineNumber() + "): " + el.getFileName() + "~>" + el.getMethodName())); // [od,
		                                                                                                          // do),
		                                                                                                          // wiÄ™c
		                                                                                                          // (1,3)
		                                                                                                          // odda
		                                                                                                          // wiersz
		                                                                                                          // 2 i 3
		// .forEach(cnsmr);
		// .toArray(ts);/
	}

	// Asercje sÄ… tylko gdy odpalimy z asercjami. Properties->Run->MV Options i
	// dodaÄ‡ "-ea"
	// Asercje sÄ… Error wiÄ™c catch(Throwable t) ich NIE ZĹ�APIE. Nalezy uĹĽyÄ‡
	// catch (AssertionError e), ale... (patrz dalej)
	// Asercje inaczej niĹĽ w Delphi powinny z zaĹ‚oĹĽenia stopowaÄ‡ program. Wpp
	// uĹĽywaÄ‡ wyjÄ…tkĂłw
	public static void Assert() {
		int ii = 4;
		assert ii == 3;
	}

	public static void Logger() throws IOException {
		// Najprostsze logowanie - globalne do System.err
		Logger.getGlobal().info("Logowanie najprostsze");

		// Logowanie ma kilka poziomow waĹĽnoĹ›c: SEVERE, WARNING, INFO, CONFIG, FINE,
		// FINER, FINEST
		Logger logger = Logger.getLogger("MojTestowyLogger");
		// Logger bÄ™dzie logowaĹ‚ wszystko od FINEST w gĂłrÄ™ (czyli wszystko)
		logger.setLevel(Level.FINEST);
		logger.info("Logowanie z poziomem 'FINEST'.");

		// Do loggera moĹĽna dodawaÄ‡ i usuwaÄ‡ handlery
		// dodatkowe logowanie do pliku
		FileHandler handler = new FileHandler("D:/a.txt");
		// ustalamy, ĹĽe logowane do pliku bÄ™da wszystkie od CONFIG w gĂłrÄ™
		handler.setLevel(Level.CONFIG);
		logger.addHandler(handler);

		// przykĹ‚ady logowaĹ„
		logger.severe("--> severe" + Arrays.asList(Thread.currentThread().getStackTrace()).toString());
		logger.warning("--> warning");
		logger.info("--> info");
		logger.config("--> config");// nie pokaĹĽe siÄ™ na konsoli
		logger.fine("--> fine");// nie pokaĹĽe siÄ™ na konsoli
		// Ustalamy poziom logowania handlera konsoli handlera (konsola)
		// Nie mamy dostÄ™pu do zaszytego handlera, wiÄ™c dodajemy nowy handler (wpisy
		// nie bÄ™dÄ… siÄ™ dublowaÄ‡)
		Handler consHand = new ConsoleHandler();
		consHand.setLevel(Level.FINER);
		logger.addHandler(consHand);
		logger.finer("--> finer");// pokaĹĽe siÄ™
		logger.finest("--> finest");// nie pokaĹĽe siÄ™

		// logowanie rzucenia wyjÄ…tku wraz ze stosem jako FINER. Exception przerobiony
		// jest na rezrezentacjÄ™ XMLowÄ…
		logger.setLevel(Level.FINER);
		handler.setLevel(Level.FINER);
		consHand.setLevel(Level.FINER);
		IOException ex = new IOException("Cannot read configuration");
		logger.throwing("com.mycompany.mylib.Reader", "read", ex);
		throw ex;
	}

	// Zastosowanie iteratora do wypeĹ‚niania listy w zadanej kolejnoĹ›ci i jej
	// edycji
	// Iterator bÄ™dzie sĹ‚uĹĽyĹ‚ do chodzenia po liĹ›cie, co pozwala na jej
	// wypeĹ‚nianie i modyfikacjÄ™
	public static void IteratorFillList() {
		List<String> friends = new ArrayList<>();
		ListIterator<String> iter = friends.listIterator();

		// Dodanie elemetu
		iter.add("Fred"); // Fred |
		System.out.println(friends);

		// Dodanie
		iter.add("Wilma"); // Fred Wilma |
		System.out.println(friends);

		// Jeden do tyĹ‚u
		iter.previous(); // Fred | Wilma
		System.out.println(friends);

		// Wstawienie onwej wartoĹ›ci we wskazane miejsce
		iter.set("Barney"); // Fred | Barney
		System.out.println(friends);

		// Dodanie elemetu
		iter.add("BamBam"); // Fred BamBam | Barney
		System.out.println(friends);
	}

}
