/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

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
        
public class Share {

    
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, Exception, Throwable {
        
    }
   
}
