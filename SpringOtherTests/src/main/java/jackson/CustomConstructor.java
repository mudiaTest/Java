package jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json wymaga istnienia pustego konstruktora ALE zamiast tego mo�na zrobi� konstruktor wskazuj�cy, jak deserializowa�.
 * Taki konstruktor mo�e mie� wi�cej parametr�w, ale przynajmniej cze�� z nich musi pokrywa� wszystkie pola z deserializowanego stringa
 * Mo�e by� TYLKO JEDEN konstruktor z @JsonProperty inacej daje b��d
 * Wszystkier parametry MUSZA posiada� adnotacje, nie mo�na miesza� w jednym konstruktorze, ale mog� by� konstruktory bez adnotacji
 * {"txt":"test","txt3":"test2"}
 */
public class CustomConstructor {

  public String txt = "t1";
  public String txt2 = "t2";
  
  // OK, bo cho� s� dodatkowe pola, to s� te� wszystkie znieb�dne  
//  CustomConstructor (@JsonProperty("txt2") String atxt, @JsonProperty("txt4")String atxt4, @JsonProperty("txt3")String atxt3)
//  {
//    this.txt = atxt;
//    this.txt2 = atxt3;
//  }
  
  // BLAD zadzia�, bo brak pola "txt3"
//  CustomConstructor (@JsonProperty("txt2") String atxt, @JsonProperty("txt4")String atxt4)
//  {
//    this.txt = atxt;
//    this.txt2 = atxt4;
//  }
  
  CustomConstructor (@JsonProperty("txt2") String atxt, @JsonProperty("txt3")String atxt3)
  {
    this.txt = atxt;
    this.txt2 = atxt3;
  }
  
  CustomConstructor (String atxt, String atxt3, Long r)
  {
    this.txt = atxt;
    this.txt2 = atxt3;
  }
  
}
