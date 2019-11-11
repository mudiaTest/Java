/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package js2;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

//import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 *
 * @author Mudia
 */

class IntWrap{
    int value;
    public IntWrap(int aval){
        value = aval;
    }
}

class Cont{
    int valInt;
    String valStr;

    @Override
    public String toString() {
        return "Cont{" + "valInt=" + valInt + ", valStr=" + valStr + '}';
    }
}

class StatiocCont{
    public int val;
    public static void StaticTest(int i){
        System.out.println(String.format("Test: '%d'", i));
    }
    public void NonStaticTest(int i){
        System.out.println(String.format("Test: '%d', %d'", val, i));
    }
}

class Employee implements InvocationHandler, Serializable, ToIntFunction{

    @Override
	public Object invoke(Object proxy, Method method, Object[] args){
        System.out.println("Proxy: " + method.getName());
        return null;
    }

    public void PrintTest(){
        System.out.println("TEST");
    }

    @Override
    public String toString() {
        return "Employee(" + hashCode() + "){" + "name=" + name + ", age=" + age + ", surname=" + surname + '}';
    }
    String name;

    public String getName() {
        return name;
    }

    String surname;

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }
    transient int age;

        private void writeObject(ObjectOutputStream out)
        throws IOException {
            out.defaultWriteObject();
            out.writeInt(age);
        }
        private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            age = in.readInt();
        }



    public Employee(){};
    public Employee(String aname){
        name = aname;
    };
    public Employee(String aname, int aage){
        name = aname; age = aage;
    };
    public Employee(String aname, int aage, String asurname){
        name = aname; age = aage; surname = asurname;
    };
    public Employee(int aage){
        age = aage;
    };

    //Consumer przyjmuje jeden parametr i niczego nie zwraca
    public void Changer(Consumer<Employee> action){
        //accept to NIE JEST akceptacja, ale WYWOĹłANIE akcji
        action.accept(this);
    };

    @Override
    public int applyAsInt(Object t) {
        return age;
    }

    public Employee SetAgeChain(int aage){
        age = aage;
        return this;
    }

    public static Integer bestLooking(Employee empl1, Employee empl2){
        return 1;
    }

    //1 jeśli el1 > el2
    //0 jeśli el1 == el2
    //-1 jeśli el1 < el2
    public static Integer older(Employee empl1, Employee empl2){
        return empl1.age>empl2.age ? 1 : (empl1.age<empl2.age ? -1 : 0);
    }
}

class EmployeeBoss extends Employee{
    private Manager1 boss;
    public void setBoss (Manager1 aboss){
        boss = aboss;
    }

    public EmployeeBoss(String aname, int aage) {
        super(aname, aage);
    }
}

class Manager1 extends Employee{
    Manager1(String name, int i) {
        super(name, i);
    }
}

class Manager1Boss extends EmployeeBoss{
    Manager1Boss(String name, int i) {
        super(name, i);
    }
}

class Manager2 extends Employee{

}

class EmployeeList extends ArrayList<Employee>{
    //pozwala na dołaczanie do listy wszystkich elementĂłw dziedziczących lub Employee
    public void addFirst(List<? extends Employee> list){
       add(list.get(0)) ;
    }
}

public class JA2 {

    /**
     * @param array
     * @param newLength
     * @param args the command line arguments
     * @return
     */

    public static <T> void swapFirstTwo(List<T> l1, List<T> l2){
        T temp = l1.get(0);
        l1.set(0, l1.get(1));
        l1.set(0, temp);
        T temp2 = l2.get(0);
        l2.set(0, l2.get(1));
        l2.set(0, temp);
    }

      public static <T,W> void swapFirstTwoDifferent(List<T> l1, List<W> l2){
        T temp = l1.get(0);
        l1.set(0, l1.get(1));
        l1.set(0, temp);
        W temp2 = l2.get(0);
        l2.set(0, l2.get(1));
        l2.set(0, temp2);
    }

    /**
     *
     * @param list
     */
    public static void AddGenListTestNoOk(List<? extends Employee> list){
        //list.add(list.get(0)); //nie zadziała - da błąd
    }

    public static <E> void addAll(Collection<? extends E> c){
        c.add(null);
    }

    public static Object[] badCopyOf(Object[] array, int newLength) { // Not useful
        Object[] newArray = new Object[newLength];
        for (int i = 0; i < Math.min(array.length, newLength); i++)
        newArray[i] = array[i];
        return newArray;
    }

    public static Object goodCopyOf(Object array, int newLength) {
        //Pobieramy obiekt Class
        Class<?> cl = array.getClass();
        if (!cl.isArray())
            return null;
        //Pobieramy klasę obiektĂłw w Array
        Class<?> componentType = cl.getComponentType();
        //Pobieramy wielkość tablicy
        int length = Array.getLength(array);
        //Tworzymy nowy obiekt tablicy o zadanej długości i type przechowywantch obiektĂłw
        Object newArray = Array.newInstance(componentType, newLength);
        //Wypełniamy nową tablicę referencjami pobranymi ze starej tablicy
        for (int i = 0; i < Math.min(length, newLength); i++)
            Array.set(newArray, i, Array.get(array, i));
        return newArray;
    }

    public static void printAll(Employee[] staff, Predicate<Employee> filter) {
        for (Employee e : staff)
        if (filter.test(e))
        System.out.println(e.getName());
    }

    public static void printAllSuper(Employee[] staff, Predicate<? super Employee> filter) {
        for (Employee e : staff)
        if (filter.test(e))
        System.out.println(e.getName());
    }

    //To jest predykat, czyli funkcjonalny interface przyjmujący jeden element wskazanego typu (<Object>) i oddający true/false
    //Predykat opisany (zdefiniowany) wyrażeniem lambda
    public static Predicate<Object> printAllPredicate(){
        return p -> p.toString().length()%2 == 0;
    }

    public static Predicate<Employee> printAllEmplPred(){
        return p -> p.toString().length()%2 == 0;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, Exception, Throwable {
        int brk;

        // TODO code application logic here

        //CreateList();
        //StringCompare();
        //SortList();
        //BasicListOper();
        //CreateObjInStream();
        //ObjectStreamToArray();
        //ArrayToList();
        //TestInterFunc();
        //ZasiegZmiennej();

        //CustomCompareOneVar();
        //CustomCompareMultiVars();

        //AnnonymousClass();

        //ClassInfo();

        //ArrCopy();

        //ClassResource();
        //AutoCloseResource();

        //ExtendExcept();
        //ExtendExceptInitCause();

        //StackTrace();

        //Assert();

        //Logger();

        //GenFunkcjeZGenArg();
        //GenFuncjeZGenArgPredicate();
        //GenInheritance();
        //GenListCreateWithImpl();
        //GenPair();

        //IteratorFillList();

        //ProperSave();
        //ProperLoad();
        //ProperJava();

        //ContBitSet();
        //ContPriorityQueue();
        //ContUnmodifList();

        //StreamFiltrMapToAtrray();
        //StreamCount();
        //StreamJoinCollect();
        //StreamSumAvgCollect
        //StreamNewArray();
        //StreamNewListCollect();
        //StreamNewMapCollect();
        //StreamGroupByCollect();
        //StreamBoolGroupCollect();
        //StreamCountGroupsCollect();
        //StreamSumGroupsCollect();
        //StreamPartitioningGruppsCollect();
        //StreamDistinct();
        //StreamSort();
        //StreamPeekForEach();
        //StreamFindFirstAnyOptional();
        //StreamPrintToScr();
        //IntStreamFromArr();
        //IntStreamFromRange();
        //StreamIntToInteger();
        //StreamBoxing();
        //StreamParallel();

        //IOPath();
        //IOBytesToArr();
        //IOByteToVar();
        //IOToString();
        //IOLinesToStringList();
        //IOWordToVar();
        //IOWordsToMap();

        //IOBufferedWriter();
        //IOPrintWriter();
        //IOOutputStreamWriter();
        //IOStringToFile();
        //IOManyStringsToFile();
        //IODataInputStream();
        //IORandomAccessFile();
        //IOBigFileFileChannel();
        //BlokadaStop();
        //BlokadaTry();

        //IOPathToFile();
        //IOCreate();
        //IOCopyMove();
        //IOParseDir();

        //IOURL();

        //RESingle();
        //REMulti();
        //REParseResult();
        //REStringSplit();
        //REStringReplace();
        //REParameters();
        //REStream();

        //SerSimple();
        SerMultiTheSame();




        brk=0;
    }

    //I/O - kopiowanie OS do IS
    public static void copy(InputStream in, OutputStream out) throws IOException{
        final int BLOCKSIZE = 1024;
        byte[] bytes = new byte[BLOCKSIZE];
        int len;
        while ((len = in.read(bytes)) != -1)//odczytuje kolejne porcje wielkości BLOCKSIZE
            out.write(bytes, 0, len); //zapisueje do stream całą tablicę (od 0 do len)
    }
    //I/O - odczytanie wszystkich bajtĂłw z pliku
    public static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        out.close();
        return out.toByteArray();
    }

    //Tworzenie i wyświetlanie list
    //System.out.pront używa metody toString()
    public static void CreateList(){
        ArrayList<String> arr1 = new ArrayList<>(Arrays.asList("Ala, Igor"));
        ArrayList<Object> arr2 = new ArrayList<>(Arrays.asList(new Cont()));
        System.out.println("Lista stringĂłw " + arr1);
        System.out.println("Lista obiektĂłw " + arr2);
    }

    //PorĂłwnywanie stringĂłw
    public static void StringCompare(){
        String a = "World";
        String greeting = "Hello, World!";
        String location = greeting.substring(7, 12);
        //== porĂłwnuje referencje więc to da błąd
        System.out.println("Błędne porĂłwanie stringĂłw '==': " + (location == "World"));
        // a to true, bo oba stringi są 'systemowe'
        System.out.println("Błędne porĂłwanie stringĂłw '==': " + ("World" == "World"));
        System.out.println("Poprawne porĂłwanie stringĂłw 'equals()': " + (location.equals("World")));
    }

    //Sotrowanie list
    public static void SortList(){
        String[] arr = {"ola", "ala", "Ala"};
        System.out.println("Pierwszy element listy (przed sortowaniem) " + arr[0]);

        Arrays.sort(arr);
        System.out.println("Pierwszy element listy (domyślne sortowanie) " + arr[0]);

        String[] arr2 = {"ola", "ala", "Ala"};
        Arrays.sort(arr2, String::compareToIgnoreCase );
        System.out.println("Pierwszy element listy (domyślne sortowanie + ignoreCase) " + arr2[0]);
    }

    //postawowe operacje na listach; usuwanie pod warunkiem i forEach
    public static void BasicListOper(){
        ArrayList<Integer> lst2 = new ArrayList<>();
        lst2.add(1);
        lst2.add(null);
        lst2.add(2);
        System.out.print("Lista przed remove: " + lst2);
        lst2.removeIf(e -> Objects.isNull(e));
        //ForEach - pętla po wszystkich elementach
        System.out.print("\nList.forEach(1) ");
        lst2.forEach(System.out::print);
        System.out.print("\nList.forEach(2) ");
        lst2.forEach(e -> System.out.print(" '" + e + "'"));
        System.out.println("");
    }

    //
    public static void CreateObjInStream(){
        List<String> names = new ArrayList<>( Arrays.asList("ola", "ala", "ala") );

        Stream<Employee> employesStream1 = names.stream().map(name -> new Employee(name) );
        System.out.println("'employesStream1'");
        employesStream1.forEach(System.out::println);

        //poniższe działa tak samo. In to lista stringĂłw i szuka konstruktora z przyjmującego jeden string
        //'name -> new Employee(name)' rĂłwnoznaczne 'Employee::new'
        Stream<Employee> employesStream2 = names.stream().map(Employee::new);
        System.out.println("'employesStream2'");
        employesStream2.forEach(System.out::println);
    }

        /*Metody technicze*/
        public static Stream<Employee> CreateEmplStream(){
            AtomicInteger counter = new AtomicInteger();
            List<String> names = new ArrayList<>( Arrays.asList("ola", "ala", "ala") );
            return names.stream().map( name -> new Employee(name, counter.getAndIncrement(), String.valueOf(counter.get())) );
        }

        public static Employee[] CreateEmplArray(){
            return CreateEmplStream().toArray( Employee[]::new );
        }

        public static List<Employee> CreateEmplList(){
            return new ArrayList<>( Arrays.asList(CreateEmplArray()) );
        }

    //Tworzenie tablicy obiektĂłw ze streama tych obiektĂłw.
    //Obiekty w tablicy so TE SAME obiekty, ktĂłre są w streamie
    public static void ObjectStreamToArray(){
        //oba poniższe tworzą Array obiektĂłw Employee o rozmiarze = ilości obiektĂłw w stream
        Employee[] emplArr1 = CreateEmplStream().toArray(Employee[]::new);
        Employee[] emplArr2 = CreateEmplStream().toArray(size -> new Employee[size]);
    }

    //Tworzenie listy obiektĂłw z tablicy tych obiektĂłw.
    //Obiekty w liście so TE SAME obiekty, ktĂłre są w tablicy
    public static void ArrayToList(){
        //poniższe odda nową listę z oryginalnymi elementami Employee pobranymi z Array, ktĂłrą można rozszerzać
        List<Employee> emplLst1 = new ArrayList<>( Arrays.asList(CreateEmplArray()) );
        //poniższe odda listę z oryginalnymi w elementami Employee pobranymi z Array,
        //ktĂłra jest defacto opakowaniem dla array, NIE MOĹ»NA jej rozszerzać
        List<Employee> emplLst2 = Arrays.asList(CreateEmplArray());
    }

    //Test włanej funkcji z parametrem IF (interfejs funkcjyjny), czyli przyjmującej LA (lambdę)
    public static void TestInterFunc(){
        Employee emplIza = new Employee("iza", 1);
        emplIza.Changer( emp -> emp.age += 2);
    }

    //Zasięg zmiennej i przechowywanie klas dla typow prostych w kontenerach i tablicach
    public static void ZasiegZmiennej(){
        //to nie zadziała (bład kompilacji!), bo WL musi mĂłc zachomikować wartość i, a zniknie po zakoĹ„czeniu iteracji pętli
        //for (int i=0; i < 4; i++){
        //    new Thread( ()->System.out.println(i) );
        //}

        //to zadziała, bo choć warości jest wiele, ale każda jest przechowywana we włanej "zmiannej" w tablicy
        int lp1 = 0;
        Thread[] threadArr = new Thread[4];
        int[] loopArr = { 0,1,2,3 };
        //UWAGA!
        for (int i: loopArr){
            threadArr[lp1] = new Thread( ()->System.out.print(i + ", ") );
            lp1++;
        }
        //Zmiana wartości nie będzie zanotowana przez Thread
        loopArr[1] = 55;
        System.out.print("Wyniki z TreadĂłw (int): ");
        for (Thread t: threadArr){
           t.run();
        }
        System.out.print("\n");


        //UWAGA !!! Tablica Integer[] przechowuje WARTOĹšCI a NIE REFERENCJE do obiektĂłw!
        //UWAGA !!! List<Integer> TEĹ» przechowuje WARTOĹšCI a NIE REFERENCJE do obiektĂłw!
        //UWAGA !!! Prawdopodobnie każdy kontener będzie tak działał z instancjami klas typĂłw prostych!
        int lp2 = 0;
        Thread[] threadArr2 = new Thread[4];
        Integer[] loopArr2 = new Integer[4];
        Integer o = new Integer(1);;
        loopArr2[0] = new Integer(0);
        loopArr2[1] = new Integer(1);
        loopArr2[2] = new Integer(2);
        loopArr2[3] = new Integer(3);
        System.out.println("---->"+loopArr2[1].hashCode());
        System.out.println("---->"+loopArr2[2].hashCode());
        for (Integer i: loopArr2){
            System.out.println("--->"+(Integer)i.hashCode());
            threadArr2[lp2] = new Thread( ()->System.out.print(i.hashCode() + "|" + i.toString() + ", ") );
            lp2++;
        }
        //Zmiana wartości NIE BÄłDZIE zanotowana przez Thread
        loopArr2[1] = 55;
        System.out.println("-->"+loopArr2[1].hashCode());
        System.out.print("Wyniki z TreadĂłw (Integer): ");
        for (Thread t: threadArr2){
           t.run();
        }
        System.out.print("\n");


        //Przekazywanie ĹşrĂłdłowych obiektĂłw
        int lp3 = 0;
        Thread[] threadArr3 = new Thread[4];
        IntWrap[] loopArr3 = new IntWrap[4];
        loopArr3[0] = new IntWrap(0);
        loopArr3[1] = new IntWrap(1);
        loopArr3[2] = new IntWrap(2);
        loopArr3[3] = new IntWrap(3);
        for (IntWrap i: loopArr3){
            threadArr3[lp3] = new Thread( ()->System.out.print(i.hashCode() + "|" + i.value + ", ") );
            lp3++;
        }
        //Zmiana wartości BÄłDZIE zanotowana przez Thread
        loopArr3[1].value = 55;
        System.out.println("-->"+loopArr3[1].hashCode());
        System.out.print("Wyniki z TreadĂłw (IntWrap/for): ");
        for (Thread t: threadArr3){
           t.run();
        }
        System.out.print("\n");
    }

    //PorĂłwnywanie po jednej zmiannej
    public static void CustomCompareOneVar(){
        Employee[] emplArr = CreateEmplArray();
        System.out.println(Arrays.toString(emplArr));
        //porĂłwnywanie z wykorzystaniem domyślnego Comparator.comparing
        Arrays.sort( emplArr, Comparator.comparing(Employee::getName) );
        //własne porĂłwnywanie (nie po literach i długości, ale tylko po długości)
        Arrays.sort( emplArr, Comparator.comparing(Employee::getName, (e1,e2)->e1.length()-e2.length()) );
        System.out.println(Arrays.toString(emplArr));
    }

    //PorĂłwnywanie po wielu zmiannych
    //Na wyniku porĂłwnania dokonujemy kolejnych porĂłwnaĹ„
    public static void CustomCompareMultiVars(){
        Employee[] emplArr = CreateEmplArray();
        emplArr[0].age = 1;
        System.out.println(Arrays.toString(emplArr));
        Arrays.sort( emplArr, Comparator.comparing(Employee::getName).thenComparing(Employee::getAge).thenComparing(Employee::getAge) );
        System.out.println(Arrays.toString(emplArr));
    }

    //Klasa anonimowa - klasa tworzona "w locie" - nadpisuje funkcję dodając jej opcję logowania
    public static void AnnonymousClass(){
        ArrayList<String> arrNewConTest = new ArrayList<String>(10)
        {
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
    public static void ClassInfo() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException{
        List<Employee> emplLst2 = CreateEmplList();
        Class<?> cl = emplLst2.getClass();

        //Nazwa klasy
        //Cannonical name- czyli jak pokazać normalną informację o pewnych klasach
        System.out.println(String.format("Nazwa klasy: %s, %s", cl.getName(), cl.getCanonicalName()));

        //Class nie udostępnia metod danej klasy (nawet statycznych), ale informacje o klasie
        Class<?> cl2 = StatiocCont.class;
        System.out.println(String.format("Nazwa klasy: %s, %s", cl2.getName(), cl2.getCanonicalName()));
        Method[] methods = cl2.getDeclaredMethods();
        System.out.println(String.format("Metody: %s", Arrays.toString(methods) ));
        System.out.println("Nazwa metody: " + methods[0].getName());

        //Pobieranie informacji o metodzie private/public etc
        int modifiers = methods[1].getModifiers(); //public static void
        System.out.println("Metoda '" + methods[0].getName() + "' modyfikatory: " + Integer.toBinaryString(modifiers));
        System.out.println("Metoda '" + methods[0].getName() + "' static (T/N): " + Modifier.isStatic(modifiers));

        //Pobieranie i modyfikacja wartości pola
        //f odpowiada polu danej klasy a nie obiektu, więc przy zmianie lub odczytaniu trzeba podać instancję klasy
        //NoSuchFieldException, IllegalAccessException
        StatiocCont stc = new StatiocCont();
        stc.val = 9;
        System.out.println("Val przed zmianą: " + stc.val);
        Field f = stc.getClass().getField("val");
        //ustalenie czegoś
        //f.setAccessible(false);
        f.setInt(stc, 6);
        System.out.println("Val po zmianie: " + f.getInt(stc));

        //Wywoływanie metod statycznych i niestatycznych
        //InvocationTargetException
        int i = 0;
        System.out.println(String.format("PrĂłba wywołania metody: %s; static: %s",  methods[i].getName(), Modifier.isStatic(methods[i].getModifiers())) );
        methods[i].invoke(null, 7);
        i = 1;
        System.out.println(String.format("PrĂłba wywołania metody: %s; static: %s",  methods[i].getName(), Modifier.isStatic(methods[i].getModifiers())) );
        try{
            methods[i].invoke(null, 7);
        } catch (Exception e) {
            System.out.println("Nie udało się wywołać metody.");
        }

        //Dynamiczne tworzenie Array danego typu hardcoded
        Employee[] emplDynCreArr = (Employee[])Array.newInstance(Employee.class, 3);
        //Dynamiczne tworzenie Array danego typu pobranego z innego obiektu
        Class<?> cpomponentType = emplDynCreArr.getClass().getComponentType();
        Employee[] emplDynCreArr2 = (Employee[])Array.newInstance(cpomponentType, 3);


        /*Employee emplPrx = Proxy.newProxyInstance(
            null,
            Employee.(),
            // Lambda expression for invocation handler
            (Object proxy, Method m, Object[] margs) -> {
            System.out.println(value + â€ś.â€ť + m.getName() +
            Arrays.toString(margs));
            return m.invoke(value, margs);
        });*/

        //Proxies - czegoś to jednak nie rozumiem
        //https://dzone.com/articles/java-dynamic-proxy
        /*Employee emp = new Employee("Arek", 7);
        Object emplPrx = Proxy.newProxyInstance(
            null,
            emp.getClass().getInterfaces(),
            // Lambda expression for invocation handler
            (Object proxy, Method m, Object[] margs) -> {
            System.out.println(emp + "." + m.getName() +
            Arrays.toString(margs));
            return m.invoke(emp, margs);
        });
        emp.PrintTest();*/
    }

    public static void ArrCopy(){
        Employee[] emplArr = CreateEmplArray();
        //Kopiowanie tablicy generycznej.
        //Kopiowanie wspĂłłdzieli obiekty.
        Employee[] emplArr2 = (Employee[])goodCopyOf(emplArr, 3);
        //Poniższe nie zadziała, bo utworzona tablica o obiektach innego typu
        //Niedozwolone jest: Employee[] b = (Employee[]) new Object[1];
        try{
            Employee[] emplArr3 = (Employee[])badCopyOf(emplArr, 3);
        } catch (Exception e){
            System.out.println("Nie udało się skopiować tablicy z użyciem 'badCopyOf'.");
        }
    }

    //Użycie Class do wczytanie resouces
    //Ważne! Resource musi być w odpowiednim katalogu
    public static void ClassResource(){
        InputStream stream1 = StatiocCont.class.getResourceAsStream("12.txt");
        InputStream stream2 = StatiocCont.class.getResourceAsStream("res/11.txt");
        InputStream stream = stream1;
        Scanner scanner = new Scanner(stream);
        //for(String line: scanner){
        //}
        do{
          System.out.println(scanner.nextLine());
        }
        while(scanner.hasNext());
    }

    //Autonatyczne zamykanie resources na koniec bloku try
    public static void AutoCloseResource() throws Exception{
        try (Scanner in = new Scanner(Paths.get("/usr/share/dict/words"));
             PrintWriter out = new PrintWriter("output.txt")) {
        }
        catch(Exception e){
            //...
        }
        finally{
            //tu nastąpi samoczynna zamknięcie i zwolnienie resource
            //...
        }

        //Można też użyć w takiej sytuacji kodu, ktĂłry łapie potencjalne Exception z kodu finally
        try {
            try (Scanner in = new Scanner(Paths.get("/usr/share/dict/words"));
                 PrintWriter out = new PrintWriter("output.txt")) {
            }
            finally{
                //...
            }
        }
        catch(Exception e){
            //...
            throw e;
        }
    }

    //Prosta zmiana jednego Exceprion na inny
    public static void ExtendExcept() throws Exception{
        try {
            //Access the database
            throw new IOException();
        } catch (IOException ex) {
            throw new Exception("database error", ex);
        }
    }

    //Zmiana jednego Exceprion na inny z użyciem initCause
    //Pomimo rzycenia Throwable, wywołując ExtendExceptInitCause w bloku try/catch możemy wyłapać Exception
    public static void ExtendExceptInitCause() throws Throwable{
        try {
            //Access the database
            throw new IOException();
        } catch (IOException ex) {
            Throwable ex2 = new Exception("database error");
            //Exception ex2 = new Exception("database error"); //też zadziała
            ex2.initCause(ex);
            throw ex2;
        }
    }

    //StackTrace - przychwytywanie i logowanie
    public static void StackTrace() throws FileNotFoundException{
        try{
            throw new Exception("TEST EXCEPTION!");
        }
        catch(Exception e){
            //drukuje ładny stos w kolejnych liniach na konsolę
            e.printStackTrace();

            //drukuje ładny stos w kolejnych liniach do wskazanego sreamu (tutaj pliku)
            File file = new File("D:\\a.txt");
            PrintStream s = new PrintStream(file);
            e.printStackTrace(s);

            //pobiera w jedną linię stos
            System.out.println("--> \n" + Arrays.asList(e.getStackTrace()).toString());
        }

        //Poniższe w każym momencie (także poza błędami) pobiera stackTrace
        String st0 = Arrays.asList(Thread.currentThread().getStackTrace()).toString();
        System.out.println("---0>" + st0);

        //Wycinek
        String st1 = Arrays.asList(Thread.currentThread().getStackTrace())
                .subList(1, 3).toString(); //[od, do), więc (1,3) odda wiersz 2 i 3
        System.out.println("---1>" + st1);

        //Stream
        Arrays.asList(Thread.currentThread().getStackTrace())
                .stream().forEach(el -> System.out.println("---2>"+"("+el.getLineNumber()+"): "+el.getFileName()+"~>"+el.getMethodName() )); //[od, do), więc (1,3) odda wiersz 2 i 3
              /*//.forEach(cnsmr);
                //.toArray(ts);*/
    }

    //Asercje są tylko gdy odpalimy z asercjami. Properties->Run->MV Options i dodać "-ea"
    //Asercje są Error więc catch(Throwable t) ich NIE ZĹłAPIE. Nalezy użyć catch (AssertionError e), ale... (patrz dalej)
    //Asercje inaczej niż w Delphi powinny z założenia stopować program. Wpp używać wyjątkĂłw
    public static void Assert(){
        int ii = 4;
        assert ii==3;
    }

    public static void Logger() throws IOException {
        //Najprostsze logowanie - globalne do System.err
        Logger.getGlobal().info("Logowanie najprostsze");

        //Logowanie ma kilka poziomow ważnośc: SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
        Logger logger = Logger.getLogger("MojTestowyLogger");
        //Logger będzie logował wszystko od FINEST w gĂłrę (czyli wszystko)
        logger.setLevel(Level.FINEST);
        logger.info("Logowanie z poziomem 'FINEST'.");

        //Do loggera można dodawać i usuwać handlery
        //dodatkowe logowanie do pliku
        FileHandler handler = new FileHandler("D:/a.txt");
        //ustalamy, że logowane do pliku będa wszystkie od CONFIG w gĂłrę
        handler.setLevel(Level.CONFIG);
        logger.addHandler(handler);

        //przykłady logowaĹ„
        logger.severe("--> severe" + Arrays.asList(Thread.currentThread().getStackTrace()).toString() );
        logger.warning("--> warning");
        logger.info("--> info");
        logger.config("--> config");//nie pokaże się na konsoli
        logger.fine("--> fine");//nie pokaże się na konsoli
            //Ustalamy poziom logowania handlera konsoli handlera (konsola)
            //Nie mamy dostępu do zaszytego handlera, więc dodajemy nowy handler (wpisy nie będą się dublować)
            Handler consHand = new ConsoleHandler();
            consHand.setLevel(Level.FINER);
            logger.addHandler(consHand);
        logger.finer("--> finer");//pokaże się
        logger.finest("--> finest");//nie pokaże się


        //logowanie rzucenia wyjątku wraz ze stosem jako FINER. Exception przerobiony jest na rezrezentację XMLową
        logger.setLevel(Level.FINER);
        handler.setLevel(Level.FINER);
        consHand.setLevel(Level.FINER);
        IOException ex = new IOException("Cannot read configuration");
        logger.throwing("com.mycompany.mylib.Reader", "read", ex);
        throw ex;
    }

        public static List<String> CreStrList(String[] arr){
            return new ArrayList<>(Arrays.asList(arr));
        }
        public static List<Integer> CreIntList(Integer[] arr){
            return new ArrayList<>(Arrays.asList(arr));
        }

    //Przykład wybodowania i wykorzystania metod przyjmujących generyczne kolekcje jako argumenty
    public static void GenFunkcjeZGenArg(){
        List<String> people = CreStrList(new String[]{"ala", "ola"});
        List<String> people2 = CreStrList(new String[]{"ala", "ola"});
        List<Integer> cars = CreIntList(new Integer[]{1,2});
        //zadziała bo obie listy mają elementy tej samej klasy
        swapFirstTwo(people, people2);
        //Błąd kompilacji, bo typy elementĂłw list są rĂłżne
        //swapFirstTwo(people, cars);
        //Zadziała, bo lażda lista może mieć swĂłj element
        swapFirstTwoDifferent(people, cars);
        swapFirstTwoDifferent(people, people2);
    }

    //Przekazywanie interfejsĂłw funkcyjnych jako argumentu
    public static void GenFuncjeZGenArgPredicate(){
        //        //Przekazywanie Predykatu a generyczność
        Employee[] emplArr = CreateEmplArray();

        //zadziała zawsze, bo e będzie widziane jako Employee i tak się skompiluje
        //public static void printAll(Employee[] staff, Predicate<Employee> filter)
        printAll(emplArr, e->e.toString().length() % 2 == 0);

        //nie zadziała, bo printAll przyjmuje predykat z elementami Employee, a printAllPredicate to predykt z typem Object
        //public static Predicate<Object> printAllPredicate()
        //printAll(emplArr3, printAllPredicate());//to nie zadziała, bo Predicate<Employee> nie jest Predicate<Object>

        //zadziała, bo printAllSuper przyjmuje predykat z elementami Employee lub przedkami
        //public static void printAllSuper(Employee[] staff, Predicate<? super Employee> filter)
        printAllSuper(emplArr, printAllPredicate());//zadziała bo Employee dziedziczy po Object
    }

    //Generyczne dodawanie elementĂłw dziedziczących lub Employ
    public static void GenInheritance(){
        EmployeeList emplList = new EmployeeList();
        emplList.add(new Employee("ala", 1));
        emplList.add(new Employee("ola", 2));
        //class Manager1 extends Employee{
        List<Manager1> manList1 = new ArrayList<>();
        manList1.add(new Manager1("Arek", 18));
        emplList.addFirst(manList1);
        System.out.println(emplList.toString());
    }

    //Tworzenie listy elemetĂłw z jednczeną implementacją
    public static void GenLIstCreateWithImpl(){
        List<Manager1> manList = new ArrayList<>(Arrays.asList(new Manager1("ala", 11),
                                                               new Manager1("ola", 12)
                                                 )
        );
    }

    //Obiekt generyxcznej pary
    public static void GenPair(){
        ImmutablePair<String, Integer> testPair = new ImmutablePair<>("ala", 21);
    }

    //Zastosowanie iteratora do wypełniania listy w zadanej kolejności i jej edycji
    //Iterator będzie służył do chodzenia po liście, co pozwala na jej wypełnianie i modyfikację
    public static void IteratorFillList(){
        List<String> friends = new ArrayList<>();
        ListIterator<String> iter = friends.listIterator();

        //Dodanie elemetu
        iter.add("Fred"); // Fred |
        System.out.println(friends);

        //Dodanie
        iter.add("Wilma"); // Fred Wilma |
        System.out.println(friends);

        //Jeden do tyłu
        iter.previous(); // Fred | Wilma
        System.out.println(friends);

        //Wstawienie onwej wartości we wskazane miejsce
        iter.set("Barney"); // Fred | Barney
        System.out.println(friends);

        //Dodanie elemetu
        iter.add("BamBam"); // Fred BamBam | Barney
        System.out.println(friends);
    }

    //Java z zasady jest wieloplatformowa, więc raczej nie zapisuje się do Register
    //Zapis jest "płaski"
    //Zapis ustawieĹ„ do pliku
    public static void ProperSave() throws IOException{
        Properties settings = new Properties();
        settings.put("width", "200");
        settings.put("os.title", "Hello, World!");
        //Poniższe padnie, bo Atrybuty PUT musza byćcastowane do stringa
        //Employee e1 = new Employee("ala", 1);
        //settings.put("pracownik", e1);

        //Zapis do pliku textowego - tworzy nowy jeśli nie ma
        try ( OutputStream out = Files.newOutputStream( Paths.get("d:/2.txt"), CREATE ) ) {
            settings.store(out, "Program Properties");
        }

        //Zapis do pliku XML - tworzy nowy jeśli nie ma i dopisuje na koĹ„cu
        try ( OutputStream out = Files.newOutputStream( Paths.get("d:/2.xml"), CREATE, TRUNCATE_EXISTING ) ) {
            settings.storeToXML(out, "Program Properties");
        }
    }

    //Wczytywanie ustawieĹ„ z pliku
    public static void ProperLoad() throws IOException{
        Properties myProp = new Properties();
        myProp.loadFromXML(Files.newInputStream(Paths.get("d:/2.xml")));
        myProp.getProperty("os.title");
    }

    //Wczytywanie ustawieĹ„ javy
    public static void ProperJava(){
        Properties jProp = System.getProperties();
        Set<Object> keys = jProp.keySet();
        //...
    }

    //Doskonała struktura do przechowywania true/false - lepsza od Boolean[] i konwersji 1001011 na int/long
    public static void ContBitSet(){
        BitSet bs = new BitSet();
        //3 wartość to true
        bs.set(3);
        System.out.println(bs.toString());
        //5 wartość to true
        bs.set(5);
        System.out.println(bs.toString());
        //3 wartość to false
        bs.flip(3);
        System.out.println(bs.toString());
    }

    //Kolejka z piorytetem. Wstawiamy i od razu sortujemy
    public static void ContPriorityQueue(){
        PriorityQueue<Employee> empAge = new PriorityQueue<>( (e1,e2) -> e1.age > e2.age ? 1 : e1.age < e2.age ? -1 : 0 );
        empAge.add(new Employee("ala2", 2));
        empAge.add(new Employee("ala1", 1));
        empAge.add(new Employee("ala3", 3));
        //Kolejność w kolejce wg age
        empAge.forEach(System.out::println);

        Employee e = empAge.remove(); //ala1
        e = empAge.remove(); //ala2
        e = empAge.remove(); //ala3
    }

    //Lista niemodyfikowalna - opakowanie dla standardowej listy
    //Listy nie można modyfikować, ale modyfikacja listy ĹşrĂłdłowej jest widoczna także w liście niemodyfikowalnej
    public static void ContUnmodifList(){
        List<Employee> emplLst = CreateEmplList();
        List<Employee> emplLstUnMod = Collections.unmodifiableList(emplLst);
        System.out.println("1 emplLst: " + emplLst);
        System.out.println("1 emplLstUnMod: " + emplLstUnMod);
        emplLst.add(new Employee());
        System.out.println("2 emplLst: " + emplLst);
        System.out.println("2 emplLstUnMod: " + emplLstUnMod);
        //przejdzie przez kompilację, ale rzuci java.lang.UnsupportedOperationException w runtime
        emplLstUnMod.add(new Employee());
    }

    //Stream: filtr, map, toArray
    public static void StreamFiltrMapToAtrray(){
        //Przykład użycie strimienia: filter, map, toArray (z rzutowaniem)
        List<Integer> ints1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,0));
        List<Integer> ints2 = Arrays.asList(
                ints1.stream().
                        filter(i -> i>4).//filtrowanie
                            map(i -> -i).//mapowanie, czyli obrabiamy element i zwracamy go
                                toArray(Integer[]::new) //tworzenie tablicy z elementĂłw
        );
        System.out.println(ints2);

        //Teraz jeszcze raz MAP, dla obiektĂłw innych niż klas prostych
        List<Employee> emplLst = CreateEmplList();
        //ZĹłE powiększanie wieku
        emplLst.stream().
                map(i -> i.age=i.age+100).//to spowoduje, że zamiast obiektĂłw Employee zwracane będą Integer
                    forEach(System.out::println);

        //PRAWIE DOBRE powiększanie wieku, ale oddaje NOWE obiekty
        emplLst.stream().
                map(i -> new Employee(i.name, i.age+100)).//to jest poprawna implementacja
                    forEach(System.out::println);

        //DOBRE powiększanie wieku, oddaje STARE obiekty
        emplLst.stream().
                map(i -> i.SetAgeChain(i.age+100)).//to jest poprawna implementacja, bo map oddaje ten sam obiet, ktĂłry dostało
                    forEach(System.out::println);
    }

    //Stream: count
    public static void StreamCount(){
        List<Employee> emlp1 = CreateEmplList();

        //Podliczenie ile obiektĂłw spełnia warunek
        long ile = emlp1.stream().
                filter(e -> e.age>=2 ).
                count();//Zliczanie
    }

    //Stream:
    public static void StreamJoinCollect(){
        List<Employee> emlp1 = CreateEmplList();

        //odda stringa z połączonych stringĂłw
        String joinedResult = emlp1.stream().
                filter(e -> e.age>1 ).
                map(e -> e.name).
                collect(Collectors.joining("|"));

        joinedResult = emlp1.stream().
                filter(e -> e.age>1 ).
                map(Employee::toString).
                collect(Collectors.joining("|"));
    }

    //Stream:
    public static void StreamSumAvgCollect(){
        List<Employee> emlp1 = CreateEmplList();

        //SUM
        IntSummaryStatistics summary = emlp1.stream().
                filter(e -> e.age>1 ).
                collect(Collectors.summarizingInt(Employee::getAge));
        //AVG
        Double summary2 = emlp1.stream().
                filter(e -> e.age>1 ).
                collect(Collectors.averagingInt(Employee::getAge));
        System.out.println(summary.getSum());
    }

    //Stream: toArray
    public static void StreamNewArray(){
        List<Employee> emlp1 = CreateEmplList();

        //wygenerowanie nowego zestawu (kopii) Employee ze zmienionymi wartościami
        List<Employee> empl2 = Arrays.asList( emlp1.stream().
                                                filter(e -> e.age>=2 ).
                                                map(e -> {return new Employee(e.name, -e.age);}).
                                                toArray(Employee[]::new)
        );
    }

    //Stream: collect.toList
    public static void StreamNewListCollect(){
        List<Employee> emlp1 = CreateEmplList();

        //Wykrojenie z obiektĂłw złożonych pojedynczych wartości
        List<Integer> collect1 =
            emlp1.stream().
                    filter(e -> e.age>=0 ).
                    map(x -> x.age).//przyjmuje obiekt Employee a oddaje Integer z jego wiekiem
                    collect(Collectors.toList());//zapisujemy do listy
        //map to wciąż są wspĂłlne obiekty z emlp1 ALE jeśli oddawany podobiekt
        //jest klasy typu prostego to oddawane są wartości i poniższe nie
        //spowoduje zmian w collect1
        emlp1.get(0).age = 88;

        //Oddajemy w kolekcji te same elementy ze ĹşrĂłdła
        List<Employee> collect2 =
            emlp1.stream().
                filter(e -> e.age>=0 ).
                collect(Collectors.toList());
        collect2.get(0).age = 4;

        //utworzy nowy set
        emlp1.stream().
                filter(e -> e.age>1 ).
                collect(Collectors.toSet());

        //utworzy nowy set zadanego typu
        //należy pamiętać o wymaganiach co do obiektĂłw, np TreeSet wymaga aby obiekt (Employee) implementował Comparable
        emlp1.stream().
                filter(e -> e.age>1 ).
                collect(Collectors.toCollection(HashSet::new));
    }

    //Stream collect.toMap
    public static void StreamNewMapCollect(){
        List<Employee> emlp1 = CreateEmplList();

        //key z obiektu i val z obiektu
        Map<Integer, String> emplMap1 =
                emlp1.stream().
                        filter(e -> e.age>1 ).
                        collect(Collectors.toMap(Employee::getAge, Employee::getName));//tworzenie nowej mapy
        //key zewnętrzne i val cały obiekt
        //UWAGA!!! Na Parralel mapa może mieć inną kolejność (patrząc po key) niż dane wejściowe
        //UWAGA2!!! Dla każdej toMap jest odpowiednik toConcurrentMap dla działaĹ„ w wielu wątkach

        //Zapewnienie licznika (incrementatora) bezpiecznego dla działaĹ„ wielowątkowych
        AtomicInteger counter = new AtomicInteger();
        Map<Integer, Employee> emplMap2 =
                emlp1.stream().
                        filter(e -> e.age>1 ).
                        collect(Collectors.toMap(s->counter.getAndIncrement(), Function.identity()));

    }

    //Grupowanie elementĂłw streama po getAge
    //Collectors.groupingBy(a)
    public static void StreamGroupByCollect(){
        List<Employee> emlp1 = CreateEmplList();
        Map<Integer, List<Employee>> emplMap3 =
            emlp1.stream().
                collect(Collectors.groupingBy(Employee::getAge));//dla każdego wieku zostanie utworzona lista Employee
    }

    //Uproszczone grupowanie na elemnty spełniające warunek true/false -
    //UWAGA!!! NIE MYLIÄ† z MSSQL partitioning
    //Collectors.partitioningBy(a)
    public static void StreamBoolGroupCollect(){
        List<Employee> emlp1 = CreateEmplList();
        Map<Boolean, List<Employee>> emplMap4 =
            emlp1.stream().
                //Utworzona zostanie mapa o kluczas true/false
                //sprawdzamy, czy elemet spełnia warunrk i dodajemy do odpowieniej listy
                collect( Collectors.partitioningBy(e -> e.getAge() > 1) );

    }

    //COUNT z SQL
    //Collectors.groupingBy(a, b) / Collectors.counting
    public static void StreamCountGroupsCollect(){
        List<Employee> emlp1 = CreateEmplList();
        Map<Integer, Long> emplMap3 =
            emlp1.stream().
                collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));

    }

    //SUM/AVG z SQL
    //Collectors.groupingBy(a, b) / Collectors.summingInt
    public static void StreamSumGroupsCollect(){
        List<Employee> emlp1 = CreateEmplList();
        //SUM
        Map<Integer, Integer> emplMap =
            emlp1.stream().
                collect(Collectors.groupingBy(Employee::getAge, Collectors.summingInt(Employee::getAge)));
        //AVG
        Map<Integer, Double> emplMap4 =
            emlp1.stream().
                collect(Collectors.groupingBy(Employee::getAge, Collectors.averagingInt(Employee::getAge)));

    }


    //MAX jednej kolumny dla grupy po innej kolumnie - coś jak MSSQL partitioning :)
    //Collectors.mapping
    public static void StreamPartitioningGruppsCollect(){
        List<Employee> emlp1 = CreateEmplList();
        Map<String, Optional<String>> emplMap = emlp1.stream().collect(
            Collectors.groupingBy(
                    Employee::getName,//grupujemy po name
                    Collectors.mapping(//wewnąrz takiej grupy wykonujemy działanie
                            Employee::getSurname, //pobieramy surname
                            Collectors.maxBy( Comparator.comparing(String::length) )//długość każdego surname wrzycamy do funkcji max
                    )
            )
        );

        System.out.println("->" + emlp1);

        Map<String, Optional<Employee>> emplMap2 = emlp1.stream().collect(
            Collectors.groupingBy(
                    Employee::getName,//grupujemy po name
                    Collectors.mapping(//wewnąrz takiej grupy wykonujemy działanie
                            e -> e, //pobieramy cały obiekt Employee
                            Collectors.maxBy( Employee::older ) //z grupy Employee bierzemy tego, ktĂłry oddaje największą wartość przy wywołaniu funkcji OLDER
                    )
            )
        );

        System.out.println("-->" + emplMap2);
    }



    //Stream: distinct
    //PorĂłwnywanie odbywa się za pomocą eq więc na new Employee("ala", 1) != new Employee("ala", 1)
    public static void StreamDistinct(){
        //porĂłwnywanie odbywa się za pomocą eq więc na new Employee("ala", 1) != new Employee("ala", 1)
        Employee empl = new Employee("ala", 1);
        //oba elementy listy dadządla eq "true"
        List<Employee> emlp3 = new ArrayList<>(Arrays.asList(empl, empl));
        //wszystkie elmenty list są rĂłzne pomomo identycznych wartość
        List<Employee> emlp4 = new ArrayList<>(Arrays.asList(new Employee("ala", 1), new Employee("ola", 2), new Employee("ala", 1)));
        List<Employee> collect3 = emlp3.stream().distinct().collect(Collectors.toList());
        List<Employee> collect4 = emlp4.stream().distinct().collect(Collectors.toList());
        System.out.println(collect3);
        System.out.println(collect4);
    }

    //Stream: Sotrowanie
    public static void StreamSort(){
        List<Employee> emlp1 = CreateEmplList();
        List<Employee> collect5 = emlp1.stream().
                sorted( (e1,e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.name, e2.name) ). //sortowanie wg customowego comparatora
                    collect(Collectors.toList());
    }

    //Stream: peek, forEach
    //Możliwość odczytu i zmiany przekazywanych obiektĂłw
    public static void StreamPeekForEach(){
        List<Employee> emlp1 = CreateEmplList();

        //Zamiana warości w ĹşrĂłdle - nie oddajemy żadnych wartości
        //Jeśli chcemy prozwadzić dalsze działania należy uzyć peek
        emlp1.stream().
            filter(e -> e.age>=2 ).
                forEach(e -> e.age++); //podniesienie wieku pracownikĂłw o 1

        List<Employee> collect5 = emlp1.stream().
                peek(e -> e.age = e.age+33).//podniesienie wieku ibiektĂłw o 33 lata
                    sorted( (e1,e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.name, e2.name) ).
                        peek(e -> System.out.println("2:"+e)).//podgląd oibektĂłw przekazywanych dalej
                            collect(Collectors.toList());
    }

    //Stream: findFirst, findAny, Optional
    public static void StreamFinfFirstAnyOptional(){
        List<Employee> emlp1 = CreateEmplList();
        //Oba oddadząjeden element, ale rĂłżnica może być w zadaniach rĂłwnoległych.
        //findAny będzie szybsze, ale nie koniecznie odda pierwszy pasujący element
        Optional<Employee> collect6 = emlp1.stream().filter(e -> e.age>=9 ).findFirst();
        Optional<Employee> collect7 = emlp1.stream().filter(e -> e.age>1 ).findAny();

        //collect6.get(); // może dać bład (jeśli optional jest pusty)
        collect7.ifPresent(v -> v.age = 99); //nie rzuci będem
    }

    //Wyświetlanie w pętli
    public static void StreamPrintToScr(){
        List<Employee> emlp1 = CreateEmplList();
        emlp1.stream().filter(e -> e.age>1 ).forEach(x -> System.out.println(x.age));
        //Jako argument przyjmiemy obekt Employee i wtkomnmy na nim toString()
        emlp1.stream().filter(e -> e.age>1 ).forEach(System.out::println);
    }

    //Tworzenie IntStream z array
    public static void IntStreamFromArr(){
        int[] intArr = {0,1,2,3,4,5,6,7,8,9};
        //indexStart(inclusive), indexEnd(Exclusive) - 0, 1
        //indexEnd(Exclusive) może być większy o 1 do ostatniego indexu
        IntStream intStr = Arrays.stream(intArr, 0, 2);
        intStr.peek(e -> System.out.println(e)).sum();
    }

    //Tworzenie IntStream za pomocą Range
    //Kolejne integery [0, 3)
    public static void IntStreamFromRange(){
        IntStream intStr2 = IntStream.range(0, 3); //0,1,2
        intStr2.forEach(System.out::println);
    }

    //Konwersja stream do intStream
    public static void StreamIntToInteger(){
        //
        Stream<String> words = Arrays.asList(new String("ala"), new String("ola")).stream();
        IntStream lengths = words.mapToInt(String::length);
        lengths.forEach(System.out::println);

        //
        words = Arrays.asList(new String("ala"), new String("ola")).stream();
        Stream<Employee> lengths2 = words.map( s -> new Employee(s, s.length()) );
        lengths2.forEach(System.out::println);
    }

    //Opakowywanie - Konwersja ze stream typu prostego do stream obiektĂłw
    public static void StreamBoxing(){
        Stream<Integer> integers = IntStream.range(0, 100).boxed();
    }

    //Wielowątkowość streamĂłw
    public static void StreamParallel(){
        //Create 1
        List<String> namesList = Arrays.asList(new String("ala"), new String("ola"));
        Stream<String> parallelWords = namesList.parallelStream();

        //Create 2
        Stream<String> parallelWords2 = Stream.of(new String[] {"ola", "ala"}).parallel();

        //Poprawne wykorzystanie PS
        Map<Integer, Long> shortWordCounts =
            parallelWords2.
                filter(s -> s.length() < 10).
                    collect( groupingBy(String::length, counting()) );

        //Wyłaczenie (domyślnego) Order by - może przyspieszyć np. distinc, limit,
        Stream<String> sample = parallelWords2.unordered().limit(2);
    }

    //Budowa obiektu path
    public static void IOPath(){
        //Ĺšcieżka nie jest walidowana
        Path p = Paths.get("...");

        //Budowanie ścieżki
        Path absolute = Paths.get("/", "home", "cay1");
        Path relative = Paths.get("myapp", "conf", "user.properties");

        //Dokleja ścieżkę, czyli będzie /home/cay/myapp/work
        Path workPath1 = absolute.resolve("myapp/work");

        //Dokleja sibling /home/myapp/work
        //UWAGA! Dal ścieżek Linux jest problem z rozpoczynającym "\" i poniższe da błąd
        //Paths.get("/", "sampleFolder").resolveSibling("myapp/work")
        Path workPath2 = absolute.resolveSibling("myapp/work");

        //Relatywizujemy 2 wzglęcem 1 - ../freud/myapp - pastępije cześć wspĂłlną (obu ścieżek) wielokotopkami
        //MĂłwiąc inaczej opisuje jak ze ścięzki A dośc do ścieżki B
        Path workPath3 = Paths.get("/home/cay1/cay2/cay3").relativize(Paths.get("/home/cay1/fred/myapp"));

        int brk = 0;
    }

    //Przelanie bajtow z InputStram do tablicy
    public static void IOBytesToArr() throws IOException{
        //wczytywanie bez dodatkowych opcji
        byte[] byteArr1 = readAllBytes(Files.newInputStream(Paths.get("C:/2.txt")));

        //dodanie opcji (czy zakładać nowe pliki etc)
        OpenOption[] oos = new OpenOption[] { /*tu można wstawić opcje*/ };
        byte[] byteArr2 = readAllBytes(Files.newInputStream(Paths.get("C:/2.txt"), oos));

        //można skrĂłcić implementację
        byte[] byteArr3 = readAllBytes(Files.newInputStream(Paths.get("C:/2.txt"), new OpenOption[] {}));
    }

    //Odczytanie pojedynczego znaku z IS (w tym przypadku z pliku)
    public static void IOByteToVar() throws IOException{
        int inp;
        InputStream inStream = Files.newInputStream(Paths.get("C:/2.txt"), new OpenOption[] { CREATE_NEW });
        //Stworzenie readera dla inputStream
        Reader in = new InputStreamReader(inStream);
        inp = in.read(); //Read robi jednocześnie Next
        char[] chars = new char[5];
        in.read(chars, 2, 3); //tablica docelowa, index od ktĂłrego zacząć wstawianie, ile wstawić - [,,1,2,]
    }

    //Wczytanie całego pliku do Stringa -
    //UWAGA!! Plik nie może być duży
    public static void IOToString() throws IOException{
        String content = new String(Files.readAllBytes(Paths.get("C:/2.txt")));
    }

    //Wczytanie wszystkich linii pliku do listy StringĂłw
    public static void IOLinesToStringList() throws IOException{
        //Za pomocą Files
        List<String> lines1 = Files.readAllLines(Paths.get("C:/2.txt"));

        //Za pomocą streama 1
        List lineList1;
        try (Stream<String> lines2 = Files.lines(Paths.get("C:/2.txt"))) {
            lineList1 = Arrays.asList(lines2.toArray());
        }

        //Za pomocą streama 2
        List lineList2;
        try (Stream<String> lines2 = Files.lines(Paths.get("C:/2.txt"))) {
            lineList2 = lines2.collect(Collectors.toList());
        }

        //Za pomocą BufferedReader
        //try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()) )) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader( Files.newInputStream(Paths.get("C:/2.txt")) ) )) {
            Stream<String> lines = reader.lines();
            //lines.collect(...)

            //lub

            reader.readLine();
        }
    }

    //Wczytanie pojedynczych słĂłw z pliku
    public static void IOWordToVar() throws IOException{
        int intWord;
        Scanner scn = new Scanner(Paths.get("C:/2.txt"));
        scn.next();//pomija słowo
        intWord = scn.nextInt();//to da Exception jeśli kolejne "słowo" nie będzie liczbą
    }

    //Wczytanie pliku jako mapy z listami słĂłw
    public static void IOWordsToMap() throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader( Files.newInputStream(Paths.get("C:/2.txt")) ) )) {
            Stream<String> lines = reader.lines();
            AtomicInteger lpline = new AtomicInteger();
            Map<Integer, List<String>> map = lines.
                    peek(System.out::println).
                    //zbudowanie mapy. Key to rosnący licznik a value to lista stringĂłw stanowiących słowa w danej liście
                    collect(Collectors.toMap( s -> lpline.getAndIncrement(), s -> Arrays.asList(s.split("[^\\S]+")) )
                    );
        }
    }

    //Writer do pliku - proste metody
    public static void IOBufferedWriter() throws IOException{
        Writer out1 = Files.newBufferedWriter(Paths.get("D:/3.txt"), StandardCharsets.UTF_8, new OpenOption[] { CREATE });
        out1.write("1 111");
        out1.write("1 222");
        out1.flush();
    }

    //Writer do pliku - dodatkowo ma print i println i pozwala formatować tekst
    public static void IOPrintWriter() throws IOException{
        PrintWriter out2 = new PrintWriter(Files.newBufferedWriter(Paths.get("D:/4.txt"), StandardCharsets.UTF_8, new OpenOption[] { CREATE }));
        out2.println("2 111");
        out2.println("2 222");
        out2.flush();
    }

    //Writer do streama - w tym przypadku stream do pliku
    public static void IOOutputStreamWriter() throws IOException{
        //Wybieramy zapis do pliku
        OutputStream outStream = Files.newOutputStream(Paths.get("D:/5.txt"), new OpenOption[] { CREATE });
        //Tu mamy papis na konsolę
        //OutputStream outStream2 = System.out;
        Writer out2 = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
        out2.write("3 111\n");
        out2.write("3 222");
        out2.flush();
    }

    //Zapis gotowego stringa (cały tekst) do pliku
    public static void IOStringToFile() throws IOException{
        String content2 = ".......\n%%%%%";
        Files.write(Paths.get("D:/6.txt"), content2.getBytes(StandardCharsets.UTF_8));
    }

    //Zapis kolecji linii do pliku - to może być cokolwiek co jest Collection<String> lub nawet Iterable<? extends CharSequence>.
    public static void IOManyStringsToFile() throws IOException{
        List<String> lines = Arrays.asList("1\n2","3"); //zapisza się 3 linie
        Files.write(Paths.get("D:/7.txt"), lines, StandardCharsets.UTF_8);
    }

    //IO danych inaczej niż jako String - jest szybszy
    public static void IODataInputStream() throws IOException{
        DataInput in2 = new DataInputStream(Files.newInputStream(Paths.get("D:/7.txt")));
        DataOutput out2 = new DataOutputStream(Files.newOutputStream(Paths.get("D:/7.txt")));
    }

    //Dostęp do dowolnej cześci pliku
    public static void IORandomAccessFile() throws IOException{
        int i = 1;
        RandomAccessFile file = new RandomAccessFile("D:/7.txt", "rw");
        //file.readLine();//wczytanie całej linii (i robi next)
        //file.read();//oddaje kod znaku (i robi next), np 1 da 49
        //file.readInt();//oddaje dziwne rzeczy - nie rozumiem tego
        //file.seek(i);//przesĂłwa karetę na wskazaną pozycję po początku pliku
        file.writeChar('a');

        //Poniższy przyklad nie dział. może kodowanie jest nierodpowiednie, albo cioś innego.
        //mechanizm jest mało potrzebny, więc nie będę się w to zagłębiał
        //Przykład ++ dla integera w pliku
        //int value = file.readInt();
        //file.seek(file.getFilePointer() - 4);
        //file.writeInt(value + 1);
    }

    //Dostęp do dużych plikĂłw - możemy zmapować fragment pliku i tylko w tym fragmencie wprowadać zmiany
    public static void IOBigFileFileChannel() throws IOException{
        //Totalnie nie wiem jednak jak tego używać ???
        FileChannel channel = FileChannel.open(Paths.get("D:/3.txt"), READ, StandardOpenOption.WRITE);
        ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, /*start*/0, /*długość bloku*/channel.size());//to działa poprawnie, czyli 2 opiszcza znak o indeksach 0 i 1 i zaczyna zapis na znaku o indeksie 2
        int offset = 0;
        byte value = buffer.get(offset); //oddaje bajtową rezprezentrację znaku, czyli dla 0 odda "49"
        //nie wiem co dokładnie oddaje get/putInt etc
        //int value = buffer.getInt(offset);
        //buffer.putInt(offset, 3);
    }

    //Blokada - czeka dopoki nie można założyć blokady
    public static void BlokadaStop() throws IOException{
        //Jeśli program (np Office) zablokował plik, to ta linia rzuci java.nio.file.FileSystemException
        //Jest możliwość, że pewne aplikacje (np natywne) mają możliwość na ostrzejsze.
        //W takim przypadku nie można liczyc na to że dojdziemy do locka, ale trzeba badać rzycane wyjątki

        FileChannel channel2 = FileChannel.open(Paths.get("D:/3.txt"), READ, StandardOpenOption.WRITE);
        FileLock lock1 = channel2.lock(); //Tutaj wątek stanie i będzie czekać dopĂłki nie założy blokady
        int i = 0;
    }

    //Blokada - czeka dopoki nie można założyć blokady
    public static void BlokadaTry() throws IOException{
        FileChannel channel = FileChannel.open(Paths.get("D:/3.txt"), READ, StandardOpenOption.WRITE);
        FileLock lock2;
        try {
            lock2 = channel.tryLock();
        }
        catch (OverlappingFileLockException e){
            //Ten wyjątek wystapi jeśli ten program już zablokował ten plik
        }

        int i = 0;
        try (FileLock lock3 = channel.lock()) {
            i = 0;
        }
    }

    //Z path dostajemy obiekt File
    public static void IOPathToFile(){
        File file = (Paths.get("D:","3.txt")).toFile();
        if (file.exists()) {/*....*/};
    }

    //Tworzenie plikĂłw i katalogĂłw
    public static void IOCreate() throws IOException{
        /*W java Directory i File mają jedną klasę. OdrĂłżniają się po isDirectory()*/ if ( ((Paths.get("D:","test2")).toFile()).isDirectory() ) ((Paths.get("D:","test2")).toFile()).delete();
        Path dirPath = Files.createDirectories(Paths.get("D:","test1") /*, Attributes*/);
        File dir = (Paths.get("D:","test1")).toFile();

        /**/ if ( ((Paths.get("D:","test2")).toFile()).isFile() ) ((Paths.get("D:","test2")).toFile()).delete();
        Files.createFile(Paths.get("D:","test2"));

        //Tworzenie tymczasowego pliku i katalogi
        Path tempFile = Files.createTempFile(Paths.get("D:/"), "MY_", ".log"); // D:/MY_462324563250.log
        Path tempDir = Files.createTempDirectory(Paths.get("D:"), "MY_");
        Path tempDir2 = Files.createTempDirectory("MY_"); //katalog tymczasowy w domyślnej lokalizacji (...)/Local/Temp/MY_(...)

        int brk = 0;
    }

    //Kopiowanie i przenoszenie
    public static void IOCopyMove() throws IOException{
        //Kopiowanie
        Files.copy(Paths.get("D:/3.txt"), Paths.get("D:/33.txt"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        /*Przeniesienie*/ Files.copy(Paths.get("D:/3.txt"), Paths.get("D:/34.txt"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        Files.move(Paths.get("D:/34.txt"), Paths.get("D:/35.txt"), StandardCopyOption.REPLACE_EXISTING);
        //Prosty delete - rzuca wyjątkiem
        Files.delete(Paths.get("D:/33.txt"));
        //Delete - nie rzyca wyjątkiem, ale oddaje info o wykonaniu działania
        boolean deleted = Files.deleteIfExists(Paths.get("D:/35.txt"));

        int brk = 0;
    }

    //Parsowanie struktury katalogĂłw (pliki i katalogi)
    public static void IOParseDir() throws IOException{
        //Płaska lista pliĂłw i katalogĂłw
        try (Stream<Path> entries1 = Files.list(Paths.get("C:\\"))) {
            entries1.forEach(System.out::println);
        }
        //Wgłąb lista pliĂłw i katalogĂłw
        try (Stream<Path> entries2 = Files.walk(Paths.get("C:", "Situ"), 2/*max głębokość*/)) {
            entries2.forEach(System.out::println);
        }
    }

    //URL wymaga innego ĹşrĂłdła informacji niż książka - tu jest bieda
    //Tu tylko podstawy pobierania informacji
    public static void IOURL() throws IOException{
        URL google = new URL("http://www.google.com");
        URLConnection connection = google.openConnection();
            //connection.setRequestProperty("Accept-Charset", "UTF-8, ISO-8859-1");// można pominąć
            //connection.setReadTimeout(1000);
            //connection.setConnectTimeout(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(google.openStream()));
        reader.lines().forEach(System.out::println);
    }

    //RegExp - użyć do znalezienia tylko pierwszego - mniej wydajne jeśli chcemy uzywac w pętli
    //Oddaje tylko wynik CZY spełnia, a nie ile razy i jak spełnia
    //matches
    public static void RESingle(){
        String regex = "\\sma[a-zA-Z]*\\s";
        CharSequence input = "Ala ma kota i nie mam tego auta";
        // "\\sma[a-zA-Z]*\\s" odda false
        // ".*\\sma[a-zA-Z]*\\s.*" odda true
        System.out.println( "0-> " + Pattern.matches(regex, input) );
    }

    //RegExp - odnajduje wszystkie wystąpienia
    //matcher1.find
    public static void REMulti(){
        //"group()" = "group(0)" - oddaje po prostu odznaleziony wynik
        //matcher.toMatchResult().start() - wkazuje na pierwszy element znalezionego ciągu
        //matcher.toMatchResult().end() - wkazuje na pierwszy element PO znalezionym ciągu
        String regex = "\\sma[a-zA-Z]*\\s";  //słowa zaczynające nie na "ma"
        CharSequence input = "Ala ma kota i nie mam tego auta";
        Pattern pattern1 = Pattern.compile(regex);
        Matcher matcher1 = pattern1.matcher(input);
        while (matcher1.find()) {
            String match1 = matcher1.group();
            System.out.print(matcher1.toMatchResult().group() + ": " + /*znaleziony wynik*/
                             matcher1.toMatchResult().start() + "-" + /*początek wyniku w ĹşrĂłdle*/
                             matcher1.toMatchResult().end()); /*koniec wyniku w ĹşrĂłdle*/
            //drugi argument subSequence tpokazuje na pierwszy NIE NALEĹ»Ä„CY do podciagu
            System.out.println(" | '" + input.subSequence(matcher1.toMatchResult().start(), matcher1.toMatchResult().end()).toString() + "'");
        }
    }

    //Parsowanie poszczegĂłlnych fragmentĂłw wyniku.
    public static void REParseResult(){
        //Każdy () będzie traktowany jako osobna grupa.
        //"group(n)" oddaje n-tą grupe w wyniku, czyli n-ty nawias. Aby się nie pomylić najlepiej naywac nawiasy
        //(?:...) zostanie pominięte. czasem dodajemy (...) w celu budowy samego regexpa, ale nie wyciągnięcia z niego informacji
        //?<...> pozwala odwołać się do grupy poprzez jej nazwę, a nie numer
        //UWAGA! Grupa oddaje ostatnie "wystąpienie", więc (?<item>...)* odda tylko jeden string
        Pattern pattern2 = Pattern.compile("(?<item>\\p{Alnum}+(?:\\s+\\p{Alnum}+)*)\\s+([A-Z]{3})([0-9.]*)");
        Matcher matcher2 = pattern2.matcher("Blackwell Toaster USD29.95\nMono TV PLN11.22");
        while (matcher2.find()) {
            String item = matcher2.group("item");//Blackwell Toaster, Mono TV
            String currency = matcher2.group(2);//USD / PLN
            String price = matcher2.group(3);  //29.95, 11.22
            int brk = 0;
        }

        String input = "ab1c1ab2c2abc3ab41c4";
        Pattern pattern3 = Pattern.compile("(ab)([0-9])+");
        Matcher matcher3 = pattern3.matcher(input);//ab1 ab2 ab41
        while (matcher3.find()) {
            String match1 = matcher3.group();
            System.out.print("'" + matcher3.toMatchResult().group(1) +
                               "', '" + matcher3.toMatchResult().group(2) +
                               ": " + matcher3.toMatchResult().start() +
                               "-" + matcher3.toMatchResult().end());
            //drugi argument subSequence pokazuje na pierwszy NIE NALEĹ»Ä„CY do podciagu
            System.out.println(" | '" + input.subSequence(matcher3.toMatchResult().start()/*pierwszy należacy do grupy*/,
                                                          matcher3.toMatchResult().end() /*pierwszy nienależacy do grupy*/).toString() + "'");
        }

        int brk = 0;
    }

    //Podział wg wzorca
    public static void REStringSplit(){
        //UWAGA! Poniższy ciąg ma 2 linie, ale split potrzktuje znak nowej lini jako
        //"zwykły" znak, więc jeden z elementĂłw będzie zawierał w sobie znak nowej linii
        String input4 = "1, 2,3,   4 \n 5, ,6";
        Pattern commas1 = Pattern.compile("\\s*,\\s*");
        String[] tokens = commas1.split(input4);
        // "1, 2, 3" turns into ["1", "2", "3", "4 "]
        int brk = 0;
        System.out.println(input4);
        System.out.println(Arrays.asList(tokens));
    }

    //Podmiana wg wzorca
    public static void REStringReplace(){
        //$1 odwołuje się do () - tak jak group
        //${minutes} odwołuje się do (?<minutes>...) - tak jak group po nazwie
        String stTime = "3:45".replaceAll(
            "(\\d{1,2}):(?<minutes>\\d{2})",
            "$1 hours and ${minutes} minutes");
            // Sets result to â€ś3 hours and 45 minutesâ€ť

        int brk = 0;
    }

    //Patramtry wyszukiwania
    public static void REParameters(){
        String input = "BlAckwell Toaster model AC4 USD29.95";
        // ".*(AC).*\\s" - odda cały string jako wynik
        // "\\w*(AC)\\w*" - odda pojedyncze słowa
        // więcej atrybutĂłw wyszukiwania w Pattern.java (CTRL+ML na CASE_INSENSITIVE)
        Pattern pattern6 = Pattern.compile("\\w*(AC)\\w*", Pattern.CASE_INSENSITIVE);
        Matcher matcher6 = pattern6.matcher(input);
        while (matcher6.find()) {
            System.out.print("'" + matcher6.toMatchResult().group(1) +
                               ": " + matcher6.toMatchResult().start() +
                               "-" + matcher6.toMatchResult().end());
            //drugi argument subSequence pokazuje na pierwszy NIE NALEĹ»Ä„CY do podciagu
            System.out.println(" | '" + input.subSequence(matcher6.toMatchResult().start()/*pierwszy należacy do grupy*/,
                                                          matcher6.toMatchResult().end() /*pierwszy nienależacy do grupy*/).toString() + "'");
        }
    }

    //regexp w streamie - pattern użyty jako predicate dopasowywany jest do każdego elementu w streamie
    public static void REStream(){
        Stream<String> strings8 = Arrays.asList("ala", "ola", "bolo").stream();
        Pattern pattern8 = Pattern.compile(".*(l)(a).*");
        //pattern8.asPredicate() oddaje predykat stworzony na podstawie regexp'a
        Stream<String> result8 = strings8.filter(pattern8.asPredicate());
        result8.forEach(s -> System.out.println("8-> "+s));
    }

    //Prosta realizacja
    public static void SerSimple() throws IOException, ClassNotFoundException{
        ObjectOutputStream out9 = new ObjectOutputStream(Files.newOutputStream(Paths.get("d:\\10.txt")));
        Employee peter9 = new Employee("Peter", 90000);
        out9.writeObject(peter9);

        ObjectInputStream in9 = new ObjectInputStream(Files.newInputStream(Paths.get("d:\\10.txt")));
        Employee empl10 = (Employee) in9.readObject();

        int brk = 0;
    }

    //Serializacja obiektĂłw z referencjami
    public static void SerMultiTheSame() throws ClassNotFoundException, IOException{
        //Serializacja obiektĂłw z referencjami powoduje zapamiętanie tych refernecji i przy odczytaniu TEGO SAMEGO podobiektu
        //Podobnie zapis kilkukrotnie tego samego obiektu nie spowoduje odczytu 2 obiektĂłw, ale tego samego obietu.
        //transient - spowoduje brak SD oznaczonego pola. Można temu zaradzić poprzed napisanie
        //    private void writeObject(ObjectOutputStream out) / private void readObject(ObjectInputStream in)
        ObjectOutputStream out11 = new ObjectOutputStream(Files.newOutputStream(Paths.get("d:\\11.txt")));
        EmployeeBoss peter11 = new EmployeeBoss("Fred", 90000);
        EmployeeBoss paul11 = new Manager1Boss("Barney", 105000);
        Manager1 mary11 = new Manager1("Mary", 180000);
        peter11.setBoss(mary11);
        paul11.setBoss(mary11);
        out11.writeObject(peter11);
        out11.writeObject(paul11);
        out11.writeObject(peter11);
        ObjectInputStream in11 = new ObjectInputStream(Files.newInputStream(Paths.get("d:\\11.txt")));
        EmployeeBoss empl11_0 = (EmployeeBoss) in11.readObject();
        EmployeeBoss empl11_1 = (EmployeeBoss) in11.readObject();
        EmployeeBoss empl11_2 = (EmployeeBoss) in11.readObject();
        //szef peter11 i paul1 to ten sam obiekt, więc zmieniając szefa w peter11 zmieniamy też jego dane w paul1
        int brk = 0;
    }

}
