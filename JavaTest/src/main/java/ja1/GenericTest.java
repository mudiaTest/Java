/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class GenericTest {
    
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
    
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, Exception, Throwable {        
        GenFunkcjeZGenArg();
        GenFuncjeZGenArgPredicate();
        GenInheritance();
        GenListCreateWithImpl();
        GenPair();        
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
    
        //Metody technicze
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
    public static void GenListCreateWithImpl(){
        List<Manager1> manList = new ArrayList<>(Arrays.asList(new Manager1("ala", 11), 
                                                               new Manager1("ola", 12)
                                                 ) 
        );
    }
    
    //Obiekt generycznej pary
    public static void GenPair(){
    	ImmutablePair<String, Integer> testPair = new ImmutablePair<>("ala", 21);
    }    
}        
         
         
         
         
         
         
         
         