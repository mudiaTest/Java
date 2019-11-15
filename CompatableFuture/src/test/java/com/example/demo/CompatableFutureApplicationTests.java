package com.example.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompatableFutureApplicationTests {

  private String doString500(String txt) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("-> doString500; " + txt);
    return "didString500" + txt + " ";
  }
  
  private String doString500_2(String txt) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("-> doString500; " + txt);
    return "didString500" + txt + " ";
  }
  
  public Future<String> calculateAsync(String s) throws InterruptedException {
    CompletableFuture<String> completableFuture = new CompletableFuture<>();
    Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(1000);
            completableFuture.complete("String: " + s);
            return null;
        });
    return completableFuture;
  }
  
  /*
   * Test funkcji
   */
  @Test
  public void dummyTest() throws InterruptedException, ExecutionException {
    Future<String> cf = calculateAsync("test");
    String r = cf.get();
    System.out.println("Result: " + r);
    System.out.println("-> End");
  }
  
  /*
   * Test podstawowego scenariusza; supplyAsync
   */
//  @Test
  public void test1() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");    
    String param = "test";
    
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> doString500(param));
    String r = cf.get();
    
    System.out.println("Result: " + r);    
    System.out.println("-> End");
  }
  
  /*
   * Test podstawowego scenariusza; supplyAsync
   */
//  @Test
  public void test2() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");    
    String param = "test";
    
    CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> doString500(param));
    cf.get();
        
    System.out.println("-> End");
  }
  
  /*
   * Test przerwania oczekiwania na wynik
   */
//  @Test
  public void test3() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");
    String param = "test";
    
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> doString500(param));
    //Przerywamy czekanie na zakoñczenie doString500, ale ta funkcja wci¹¿ dzia³a w tle
    cf = CompletableFuture.completedFuture("Dummy");
    String r = cf.get();
    
    System.out.println("Result: " + r);
    Thread.sleep(2000);
    System.out.println("-> End");
  }

  /*
   * Test ods³ugi wyniku
   */
//  @Test
  public void test4() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");    
    String param = "test";
    
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> doString500(param));
    cf = cf.thenApply(s->s+" after");    
    String r= cf.get();
    
    System.out.println("Result: " + r);
    System.out.println("-> End");
  }
  
  /*
   * Test ³¹ñcucha 3 wykonañ
   */
//  @Test
  public void test5() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");    
    String param = "test";
    
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> doString500("1"))
        .thenCompose(s -> CompletableFuture.supplyAsync(() -> doString500("2")))
        .thenCompose(s -> CompletableFuture.supplyAsync(() -> doString500("3")));
    //Inny sposób osi¹gniêcia tego samego pozwalaj¹cy na wykonywanie innego kody w miêdzyczasie
//    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> doString500("1"));
//    //Some code
//    cf = cf.thenCompose(s -> CompletableFuture.supplyAsync(() -> doString500("2")));    
//    cf = cf.thenCompose(s -> CompletableFuture.supplyAsync(() -> doString500("3")));    
    
    String r= cf.get();
    
    System.out.println("Result: " + r);
    System.out.println("-> End");
  }
  
  /*
   * Test wykonania 3 akcji i obs³ugi ich wyniku
   */
//  @Test
  public void test6() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");    
    String param = "test";
    
    /*
     * Wykonanie 3 akcji i po wykonaniu wszystkich 3 obs³uga ich wyników
     * cf.get() oczekuje na wykonanie wszystkich akcji.
     * Kolejnoœæ obs³ugi wyników nie zalezy o oklejnoœci zakoñczenia akcji,
     * ale od kolejnoœci ich zadeklarowania.
     */ 
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> doString500("1"))
        .thenCombine(
            CompletableFuture.supplyAsync(() -> doString500_2("2")), 
            (s1, s2)->s1 + s2
            )
        .thenCombine(
            CompletableFuture.supplyAsync(() -> doString500("3")), 
            (s1, s2)->s1 + s2
            );
    String r= cf.get();
    
    System.out.println("Result: " + r);
    System.out.println("-> End");
  }
  
  /*
   * Test wykonania 3 akcji BEZ obs³ugi wyniku.
   * cf.get() oczekuje na wykonanie wszystkich akcji.
   */
//  @Test
  public void test7() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");    
    String param = "test";
    
    //
    CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> doString500("1"))
        .thenAcceptBoth(
            CompletableFuture.supplyAsync(() -> doString500_2("2")), 
            (s1, s2)->{/*NIE MO¯NA ZWRACAÆ*/}
            )
        .thenAcceptBoth(
            CompletableFuture.supplyAsync(() -> doString500("3")), 
            (s1, s2)->{}
            );    
    cf.get();
    
    System.out.println("-> End");
  }
  
  /*
   * Test wykonywania wielu akcji jednoczeœnie - bez zwracania wyników (czyli podobnie jak powy¿ej), ale uproszczone. 
   * cf.get() oczekuje na wykonanie wszystkich akcji.
   */
//  @Test
  public void test8() throws InterruptedException, ExecutionException {
    System.out.println("-> Start");    
    String param = "test";
    
    CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> doString500("1"));
    CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> doString500_2("2"));
    CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> doString500("3"));
    CompletableFuture<Void> ccf = CompletableFuture.allOf(cf1, cf2, cf3);
    ccf.get();
    
    String r = Stream.of(cf1, cf2, cf3).map(CompletableFuture::join).collect(Collectors.joining(" "));
    //r = cf1.get();
    
    System.out.println("Result: " + r);
    System.out.println("-> End");
  }
}
