package ja1;

class Pracownik {
  int intVal1 = 1;
  int intVal2 = 2;

  public int DajIntVal1() {
    return intVal1;
  }

  public int DajIntVal2Orig() {
    return intVal1;
  }
}

class Pracownik2 extends Pracownik {
  int intVal1 = 21; // definicja zmiannej o tej samej nazwie przykrywa poprzenią zmianną
  int intVal22 = 22;

  public int DajIntVal1() {
    return intVal1;
  }

  public int DajIntVal2() {
    return intVal2;
  }

  public int DajSuperIntVal() {
    return super.DajIntVal1();
  }

  public int DajIntVal22() {
    return intVal22;
  }
}

public class ClassTest {

  public static void main(String[] args) {
    Test1();
  }

  public static void Test1() {
    Pracownik2 p = new Pracownik2();
    System.out.println("p.DajIntVal1(): " + p.DajIntVal1());
    System.out.println("p.DajIntVal2(): " + p.DajIntVal2());// Wywołanie nowej funkcji
    System.out.println("p.DajIntVal2Orig(): " + p.DajIntVal2Orig());// Pracownik.wywołanie funkcji
    System.out.println("p.DajSuperIntVal(): " + p.DajSuperIntVal());
    System.out.println("p.DajIntVal22(): " + p.DajIntVal22());
  }

}
