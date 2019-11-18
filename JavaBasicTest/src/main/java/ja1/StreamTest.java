
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

	public static void main(String[] args) /*
	                                        * throws NoSuchFieldException, IllegalArgumentException,
	                                        * IllegalAccessException, InvocationTargetException, IOException, Exception,
	                                        * Throwable
	                                        */ {
		InputStreamToStream();
		StreamFiltrMapToAtrray();
		StreamCount();
		StreamJoinCollect();
		StreamSumAvgCollect();
		StreamNewArray();
		StreamNewListCollect();
		StreamNewMapCollect();
		StreamGroupByCollect();
		StreamBoolGroupCollect();
		StreamCountGroupsCollect();
		StreamSumGroupsCollect();
		StreamPartitioningGruppsCollect();
		StreamDistinct();
		StreamSort();
		StreamPeekForEach();
		StreamFindFirstAnyOptional();
		StreamPrintToScr();
		IntStreamFromArr();
		IntStreamFromRange();
		StreamIntToInteger();
		StreamBoxing();
		StreamParallel();
	}

	//
	public static void InputStreamToStream() {
		InputStream outFromProc = null;
		// 1
		Reader r = new InputStreamReader(outFromProc);
		BufferedReader br = new BufferedReader(r);
		Stream<String> lines1 = br.lines();
		// 2
		Stream<String> lines2 = (new BufferedReader(new InputStreamReader(outFromProc))).lines();
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
	
	public static String intToStr(Object i) {
		return i.toString();
	}
	
	

	// Stream: filtr, map, toArray
	public static void StreamFiltrMapToAtrray() {
		// Przykład użycie strimienia: filter, map, toArray (z rzutowaniem)
		List<Integer> ints1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));
		List<Integer> ints2 = Arrays.asList(ints1.stream().filter(i -> i > 4).// filtrowanie
		    map(i -> -i).// mapowanie, czyli obrabiamy element i zwracamy go
		    toArray(Integer[]::new) // tworzenie tablicy z elementów
		);
		System.out.println(ints2);		
		// Teraz jeszcze raz MAP, dla obiektów innych niż klas prostych
		List<Employee> emplLst = CreateEmplList();
		// ZĹ�E powiększanie wieku
		emplLst.stream().map(i -> i.age = i.age + 100).// to spowoduje, że zamiast obiektów Employee zwracane będą
		                                               // Integer
		    forEach(System.out::println);

		// PRAWIE DOBRE powiększanie wieku, ale oddaje NOWE obiekty
		emplLst.stream().map(i -> new Employee(i.name, i.age + 100)).// to jest poprawna implementacja
		    forEach(StreamTest::intToStr); //Odwołanie do statycznej metody przyjmującej jeden parametr

		// DOBRE powiększanie wieku, oddaje STARE obiekty
		emplLst.stream().map(i -> i.SetAgeChain(i.age + 100)).// to jest poprawna implementacja, bo map oddaje ten sam
		                                                      // obiet, który dostało
		    forEach(System.out::println);
	}

	// Stream: count
	public static void StreamCount() {
		List<Employee> emlp1 = CreateEmplList();

		// Podliczenie ile obiektów spełnia warunek
		long ile = emlp1.stream().filter(e -> e.age >= 2).count();// Zliczanie
	}

	// Stream:
	public static void StreamJoinCollect() {
		List<Employee> emlp1 = CreateEmplList();

		// odda stringa z połączonych stringów
		String joinedResult = emlp1.stream().filter(e -> e.age > 1).map(e -> e.name).collect(Collectors.joining("|"));

		joinedResult = emlp1.stream().filter(e -> e.age > 1).map(Employee::toString).collect(Collectors.joining("|"));
	}

	// Stream:
	public static void StreamSumAvgCollect() {
		List<Employee> emlp1 = CreateEmplList();

		// SUM
		IntSummaryStatistics summary = emlp1.stream().filter(e -> e.age > 1)
		    .collect(Collectors.summarizingInt(Employee::getAge));
		// AVG
		Double summary2 = emlp1.stream().filter(e -> e.age > 1).collect(Collectors.averagingInt(Employee::getAge));
		System.out.println(summary.getSum());
	}

	// Stream: toArray
	public static void StreamNewArray() {
		List<Employee> emlp1 = CreateEmplList();

		// wygenerowanie nowego zestawu (kopii) Employee ze zmienionymi wartościami
		List<Employee> empl2 = Arrays.asList(emlp1.stream().filter(e -> e.age >= 2).map(e -> {
			return new Employee(e.name, -e.age);
		}).toArray(Employee[]::new));
	}

	// Stream: collect.toList
	public static void StreamNewListCollect() {
		List<Employee> emlp1 = CreateEmplList();

		// Wykrojenie z obiektów złożonych pojedynczych wartości
		List<Integer> collect1 = emlp1.stream().filter(e -> e.age >= 0).map(x -> x.age).// przyjmuje obiekt Employee a
		                                                                                // oddaje Integer z jego wiekiem
		    collect(Collectors.toList());// zapisujemy do listy
		// map to wciąż są wspólne obiekty z emlp1 ALE jeśli oddawany podobiekt
		// jest klasy typu prostego to oddawane są wartości i poniższe nie
		// spowoduje zmian w collect1
		emlp1.get(0).age = 88;

		// Oddajemy w kolekcji te same elementy ze Źródła
		List<Employee> collect2 = emlp1.stream().filter(e -> e.age >= 0).collect(Collectors.toList());
		collect2.get(0).age = 4;

		// utworzy nowy set
		emlp1.stream().filter(e -> e.age > 1).collect(Collectors.toSet());

		// utworzy nowy set zadanego typu
		// należy pamiętać o wymaganiach co do obiektów, np TreeSet wymaga aby
		// obiekt (Employee) implementował Comparable
		emlp1.stream().filter(e -> e.age > 1).collect(Collectors.toCollection(HashSet::new));
	}

	// Stream collect.toMap
	public static void StreamNewMapCollect() {
		List<Employee> emlp1 = CreateEmplList();

		// key z obiektu i val z obiektu
		Map<Integer, String> emplMap1 = emlp1.stream().filter(e -> e.age > 1)
		    .collect(Collectors.toMap(Employee::getAge, Employee::getName));// tworzenie nowej mapy
		// key zewnętrzne i val cały obiekt
		// UWAGA!!! Na Parralel mapa może mieć inną kolejność (patrząc po key)
		// niż dane wejściowe
		// UWAGA2!!! Dla każdej toMap jest odpowiednik toConcurrentMap dla działaĹ„ w
		// wielu wątkach

		// Zapewnienie licznika (incrementatora) bezpiecznego dla działaĹ„
		// wielowątkowych
		AtomicInteger counter = new AtomicInteger();
		Map<Integer, Employee> emplMap2 = emlp1.stream().filter(e -> e.age > 1)
		    .collect(Collectors.toMap(s -> counter.getAndIncrement(), Function.identity()));

	}

	// Grupowanie elementów streama po getAge
	// Collectors.groupingBy(a)
	public static void StreamGroupByCollect() {
		List<Employee> emlp1 = CreateEmplList();
		Map<Integer, List<Employee>> emplMap3 = emlp1.stream().collect(Collectors.groupingBy(Employee::getAge));// dla
		                                                                                                        // każdego
		                                                                                                        // wieku
		                                                                                                        // zostanie
		                                                                                                        // utworzona
		                                                                                                        // lista
		                                                                                                        // Employee
	}

	// Uproszczone grupowanie na elemnty spełniające warunek true/false -
	// UWAGA!!! NIE MYLIÄ† z MSSQL partitioning
	// Collectors.partitioningBy(a)
	public static void StreamBoolGroupCollect() {
		List<Employee> emlp1 = CreateEmplList();
		Map<Boolean, List<Employee>> emplMap4 = emlp1.stream().
		// Utworzona zostanie mapa o kluczas true/false
		// sprawdzamy, czy elemet spełnia warunrk i dodajemy do odpowieniej listy
		    collect(Collectors.partitioningBy(e -> e.getAge() > 1));

	}

	// COUNT z SQL
	// Collectors.groupingBy(a, b) / Collectors.counting
	public static void StreamCountGroupsCollect() {
		List<Employee> emlp1 = CreateEmplList();
		Map<Integer, Long> emplMap3 = emlp1.stream()
		    .collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));

	}

	// SUM/AVG z SQL
	// Collectors.groupingBy(a, b) / Collectors.summingInt
	public static void StreamSumGroupsCollect() {
		List<Employee> emlp1 = CreateEmplList();
		// SUM
		Map<Integer, Integer> emplMap = emlp1.stream()
		    .collect(Collectors.groupingBy(Employee::getAge, Collectors.summingInt(Employee::getAge)));
		// AVG
		Map<Integer, Double> emplMap4 = emlp1.stream()
		    .collect(Collectors.groupingBy(Employee::getAge, Collectors.averagingInt(Employee::getAge)));

	}

	// MAX jednej kolumny dla grupy po innej kolumnie - coś jak MSSQL partitioning
	// :)
	// Collectors.mapping
	public static void StreamPartitioningGruppsCollect() {
		List<Employee> emlp1 = CreateEmplList();
		Map<String, Optional<String>> emplMap = emlp1.stream().collect(Collectors.groupingBy(Employee::getName, // grupujemy
		                                                                                                        // po name
		    Collectors.mapping(// wewnąrz takiej grupy wykonujemy działanie
		        Employee::getSurname, // pobieramy surname
		        Collectors.maxBy(Comparator.comparing(String::length))// długość każdego surname wrzycamy do funkcji max
				)));

		System.out.println("->" + emlp1);

		Map<String, Optional<Employee>> emplMap2 = emlp1.stream().collect(Collectors.groupingBy(Employee::getName, // grupujemy
		                                                                                                           // po
		                                                                                                           // name
		    Collectors.mapping(// wewnąrz takiej grupy wykonujemy działanie
		        e -> e, // pobieramy cały obiekt Employee
		        Collectors.maxBy(Employee::older) // z grupy Employee bierzemy tego, który oddaje największą wartość
		                                          // przy wywołaniu funkcji OLDER
				)));

		System.out.println("-->" + emplMap2);
	}

	// Stream: distinct
	// Porównywanie odbywa się za pomocą eq więc na new Employee("ala", 1) !=
	// new Employee("ala", 1)
	public static void StreamDistinct() {
		// porównywanie odbywa się za pomocą eq więc na new Employee("ala", 1) !=
		// new Employee("ala", 1)
		Employee empl = new Employee("ala", 1);
		// oba elementy listy dadządla eq "true"
		List<Employee> emlp3 = new ArrayList<>(Arrays.asList(empl, empl));
		// wszystkie elmenty list są rózne pomomo identycznych wartość
		List<Employee> emlp4 = new ArrayList<>(
		    Arrays.asList(new Employee("ala", 1), new Employee("ola", 2), new Employee("ala", 1)));
		List<Employee> collect3 = emlp3.stream().distinct().collect(Collectors.toList());
		List<Employee> collect4 = emlp4.stream().distinct().collect(Collectors.toList());
		System.out.println(collect3);
		System.out.println(collect4);
	}

	// Stream: Sotrowanie
	public static void StreamSort() {
		List<Employee> emlp1 = CreateEmplList();
		List<Employee> collect5 = emlp1.stream().sorted((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.name, e2.name))
		    . // sortowanie wg customowego comparatora
		    collect(Collectors.toList());
	}

	// Stream: peek, forEach
	// Możliwość odczytu i zmiany przekazywanych obiektów
	public static void StreamPeekForEach() {
		List<Employee> emlp1 = CreateEmplList();

		// Zamiana warości w Źródle - nie oddajemy żadnych wartości
		// Jeśli chcemy prozwadzić dalsze działania należy uzyć peek
		emlp1.stream().filter(e -> e.age >= 2).forEach(e -> e.age++); // podniesienie wieku pracowników o 1

		List<Employee> collect5 = emlp1.stream().peek(e -> e.age = e.age + 33).// podniesienie wieku ibiektów o 33 lata
		    sorted((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.name, e2.name))
		    .peek(e -> System.out.println("2:" + e)).// podgląd oibektów przekazywanych dalej
		    collect(Collectors.toList());
	}

	// Stream: findFirst, findAny, Optional
	public static void StreamFindFirstAnyOptional() {
		List<Employee> emlp1 = CreateEmplList();
		// Oba oddadząjeden element, ale różnica może być w zadaniach
		// równoległych.
		// findAny będzie szybsze, ale nie koniecznie odda pierwszy pasujący element
		Optional<Employee> collect6 = emlp1.stream().filter(e -> e.age >= 9).findFirst();
		Optional<Employee> collect7 = emlp1.stream().filter(e -> e.age > 1).findAny();

		// collect6.get(); // może dać bład (jeśli optional jest pusty)
		collect7.ifPresent(v -> v.age = 99); // nie rzuci będem
	}

	// Wyświetlanie w pętli
	public static void StreamPrintToScr() {
		List<Employee> emlp1 = CreateEmplList();
		emlp1.stream().filter(e -> e.age > 1).forEach(x -> System.out.println(x.age));
		// Jako argument przyjmiemy obekt Employee i wtkomnmy na nim toString()
		emlp1.stream().filter(e -> e.age > 1).forEach(System.out::println);
	}

	// Tworzenie IntStream z array
	public static void IntStreamFromArr() {
		int[] intArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		// indexStart(inclusive), indexEnd(Exclusive) - 0, 1
		// indexEnd(Exclusive) może być większy o 1 do ostatniego indexu
		IntStream intStr = Arrays.stream(intArr, 0, 2);
		intStr.peek(e -> System.out.println(e)).sum();
	}

	// Tworzenie IntStream za pomocą Range
	// Kolejne integery [0, 3)
	public static void IntStreamFromRange() {
		IntStream intStr2 = IntStream.range(0, 3); // 0,1,2
		intStr2.forEach(System.out::println);
	}

	// Konwersja stream do intStream
	public static void StreamIntToInteger() {
		//
		Stream<String> words = Arrays.asList("ala", "ola").stream();
		IntStream lengths = words.mapToInt(String::length);
		lengths.forEach(System.out::println);

		//
		words = Arrays.asList("ala", "ola").stream();
		Stream<Employee> lengths2 = words.map(s -> new Employee(s, s.length()));
		lengths2.forEach(System.out::println);
	}

	// Opakowywanie - Konwersja ze stream typu prostego do stream obiektów
	public static void StreamBoxing() {
		Stream<Integer> integers = IntStream.range(0, 100).boxed();
	}

	// Wielowątkowość streamów
	public static void StreamParallel() {
		// Create 1
		List<String> namesList = Arrays.asList("ala", "ola");
		Stream<String> parallelWords = namesList.parallelStream();

		// Create 2
		Stream<String> parallelWords2 = Stream.of(new String[] { "ola", "ala" }).parallel();

		// Poprawne wykorzystanie PS
		Map<Integer, Long> shortWordCounts = parallelWords2.filter(s -> s.length() < 10)
		    .collect(groupingBy(String::length, counting()));

		// Wyłaczenie (domyślnego) Order by - może przyspieszyć np. distinc, limit,
		Stream<String> sample = parallelWords2.unordered().limit(2);
	}
}
