package com.my.pl;

interface IA {
  //public Integer doit();
}
interface IB extends IA {
}

public class AAA {
  public IA doit() {
    return null;
  }
  
  public void test() {
    BBB b = new BBB();
    b.doit();
  }
}

class BBB extends AAA{
  @Override
  public IB doit() {
    return null;
  }
}
