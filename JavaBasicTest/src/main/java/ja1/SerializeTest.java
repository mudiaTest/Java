/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SerializeTest {

  public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
      InvocationTargetException, IOException, Exception, Throwable {
    SerSimple();
    SerMultiTheSame();
  }

  // Prosta realizacja
  public static void SerSimple() throws IOException, ClassNotFoundException {
    ObjectOutputStream out9 = new ObjectOutputStream(Files.newOutputStream(Paths.get("d:\\10.txt")));
    Employee peter9 = new Employee("Peter", 90000);
    out9.writeObject(peter9);

    ObjectInputStream in9 = new ObjectInputStream(Files.newInputStream(Paths.get("d:\\10.txt")));
    Employee empl10 = (Employee) in9.readObject();

    int brk = 0;
  }

  // Serializacja obiektów z referencjami
  public static void SerMultiTheSame() throws ClassNotFoundException, IOException {
    // Serializacja obiektów z referencjami powoduje zapamiętanie tych refernecji i
    // przy odczytaniu TEGO SAMEGO podobiektu
    // Podobnie zapis kilkukrotnie tego samego obiektu nie spowoduje odczytu 2
    // obiektów, ale tego samego obietu.
    // transient - spowoduje brak SD oznaczonego pola. Można temu zaradzić poprzed
    // napisanie
    // private void writeObject(ObjectOutputStream out) / private void
    // readObject(ObjectInputStream in)
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
    // szef peter11 i paul1 to ten sam obiekt, więc zmieniając szefa w peter11
    // zmieniamy też jego dane w paul1
    int brk = 0;
  }
}
