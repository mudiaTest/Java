/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class PropertyTest {
    
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, Exception, Throwable {                        
        ProperSave();
        ProperLoad();
        ProperJava();

        ContBitSet();
        ContPriorityQueue();
        ContUnmodifList();          
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
    
    //Java z zasady jest wieloplatformowa, więc raczej nie zapisuje się do Register
    //Zapis jest "płaski"
    //Zapis ustawień do pliku
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
        
        //Zapis do pliku XML - tworzy nowy jeśli nie ma i dopisuje na końcu
        try ( OutputStream out = Files.newOutputStream( Paths.get("d:/2.xml"), CREATE, TRUNCATE_EXISTING ) ) {
            settings.storeToXML(out, "Program Properties");
        }       
    }
    
    //Wczytywanie ustawień z pliku
    public static void ProperLoad() throws IOException{
        Properties myProp = new Properties();
        myProp.loadFromXML(Files.newInputStream(Paths.get("d:/2.xml")));
        myProp.getProperty("os.title");  
    }
    
    //Wczytywanie ustawień javy
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
    //Listy nie można modyfikować, ale modyfikacja listy źródłowej jest widoczna także w liście niemodyfikowalnej
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
}
