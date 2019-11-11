package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.EnumExample.MealEnum1;
import com.example.demo.EnumExample.MealEnum2;
import com.example.demo.EnumExample.MealEnum3;

@SpringBootTest
class EnumTest {

  @Test
  public void test01(){
    MealEnum1 me1;
    MealEnum2 me2;
    MealEnum3 me3;

    me1 = MealEnum1.PIZZA;
    me2 = MealEnum2.PIZZA;
    me3 = MealEnum3.PIZZA;

    System.out.println(me1);
    System.out.println(me1.name());
    System.out.println(me1.ordinal());
    System.out.println(me1.valueOf("HAMBURGER"));

    System.out.println(me2);
    System.out.println(me2.name());
    System.out.println(me2.ordinal());
    System.out.println(me2.getValue());
    System.out.println(me2.valueOf("HAMBURGER"));

    System.out.println(me3);
    System.out.println(me3.name());
    System.out.println(me3.ordinal());
    System.out.println(me3.getValue());
    System.out.println(me3.valueOf("HAMBURGER"));
  }
}
