package com.example.demo;

public class EnumExample {

  public enum MealEnum1
  {
    PIZZA,
    HAMBURGER;
  }

  public enum MealEnum2
  {
    PIZZA(2),
    HAMBURGER(4);

    private int value;

    public int getValue() {
      return value;
    }
    MealEnum2(int val){
      value = val;
    }
  }

  public enum MealEnum3
  {
    PIZZA("P"),
    HAMBURGER("H");

    private String value;

    public String getValue() {
      return value;
    }
    MealEnum3(String val){
      value = val;
    }
  }


}