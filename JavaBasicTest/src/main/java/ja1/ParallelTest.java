/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

class Queue {
  class Node {
    Object value;
    Node next;
  };

  private Node head;
  private Node tail;

  public synchronized void add(Object newValue) {
    Node n = new Node();
    if (head == null)
      head = n;
    else
      tail.next = n;
    tail = n;
    tail.value = newValue;
  }

  public synchronized Object get() throws InterruptedException {
    while (head == null) // w pętli próbuje pobrać obiekt po 1,5 sek. Ta akcja blokuje wsztystkie bloki
                         // synchronized(this)
      Thread.currentThread().sleep(1500);
    Node n = head;
    head = n.next;
    return n.value;
  }

  public synchronized void add2(Object newValue) {
    Node n = new Node();
    if (head == null)
      head = n;
    else
      tail.next = n;
    tail = n;
    // budzi JEDEN LOSOWY uśpiony wątek
    // notify();
    // budzi WSZYSTKIE uśpione wątki.
    // UWAGA! Jeśli obudzony wątek też nie będzie mógł pracować I NIE rzuci
    // notify, to program wpadnie w deadlock
    notifyAll();
    tail.value = newValue;
  }

  public synchronized Object get2() throws InterruptedException {
    if (head == null)
      wait(); // nie blokuje, ale czeka na notify od innych synchronized z tym samym lock
    Node n = head;
    head = n.next;
    return n.value;
  }
}

public class ParallelTest {

  // Volatile sprawia, że zmianna jest widoczna dla wszystkich wątków. Wpp wartość
  // zmiennej nie zostanie odświerzona.
  // Volatile NIE ZAPEWNI locka na zmiennej, więc należy uzywać TYLKO jeśli jedno
  // ze źródeł zapisuje a reszta czyta
  // Zaleca się użycie FINAL dla zmiennych, które nie będą ulegały zmianie.
  private static volatile boolean done = false;

  static Callable<Integer> task1 = () -> {
    int counter = (new Random()).nextInt(5) + 5;

    for (int i = 0; i < counter; i++) {
      System.out.println("Iteracja task1 " + i);
      Thread.sleep(500);
    }
    return counter;
  };

  static Callable<Integer> task2 = () -> {
    int counter = (new Random()).nextInt(5) + 5;

    for (int i = 0; i < counter; i++) {
      System.out.println("Iteracja task2 " + i);
      Thread.sleep(300);
    }
    return counter;
  };

  public static void main(String[] args) throws Exception {
//      RunTask();
//      RunCallableCancel();
//      RunCallableDontStop();
//      RunCallableWaitForOne();
//      RunCallableWaitForFirst();
//      RunCallableWaitForAllTimeout();
//      ShareTest1();
//      ParrarelStream();
//      ParrarelArraysFill();
//      ParrarelArraysSort();
//      ConcurrentHashMap();
//      RunQueue();
//      Locks();
//      SyncTest1();
//      SyncTest2();
//      Wait1();
//      Wait2();

//      ThreadSimple();
//      ThreadStop();
//      ThreadMisc();
//      ThreadLong();
//      Chain();      

//      ParallelCounter();
    System.out.println("Koniec");
  }

  // Podstawy - budowa i uruchomienie taska
  public static void RunTask() {
    Runnable task0 = () -> {
      try {
        for (int i = 0; i < 5; i++) {
          System.out.println("*");
          Thread.sleep(500);
        }
      } catch (InterruptedException ignored) {
      }
    };
    // Użyć gdy taski głolwnie leżą odłogiem
    Executor exec = Executors.newCachedThreadPool();
    // Użyć gdy taski "mocno pracują" -
    // Executor exec = Executors.newFixedThreadPool(nthreads);
    exec.execute(task0);
    System.out.println("END");

  }

  public static void RunCallableCancel() throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // Przerwanie wykoanania
    Future<Integer> taskRes1 = exec.submit(task1);
    Thread.sleep(1500);
    taskRes1.cancel(true);
    System.out.println("Wykonano " + taskRes1.get()); // rzuci błędem, bo wywołanie został cancel
  }

  public static void RunCallableDontStop() throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // Oczekiwanie na wynik pojedynczego wątku - program dalej działa i można
    // kontrolować wątek
    Future<Integer> taskRes2 = exec.submit(task1);
    while (!taskRes2.isDone()) {
      Thread.sleep(100);
    }
    System.out.println("Wykonano " + taskRes2.get() + " iteracji");
  }

  public static void RunCallableWaitForOne() throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // Oczekiwanie na wynik pojedynczego wątku - program stoi czekając
    Integer taskRes2 = exec.invokeAny(new ArrayList<>(Arrays.asList(task1)));
    System.out.println("Wykonano " + taskRes2 + " iteracji");
  }

  public static void RunCallableWaitForFirst() throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // Oczekiwanie na wynik wielu wątków - program stoi
    List<Callable<Integer>> tasks3 = new ArrayList<>();
    tasks3.add(task1);
    tasks3.add(task2);
    System.out.println("Wykonano " + " iteracji");
    // Future<Integer> results31 = invokeAny(tasks3);// odda tylko pierwszy wykonany
    // task, a resztę zrobi cancel
  }

  public static void RunCallableWaitForAllTimeout() throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // Oczekiwanie na wynik wielu wątków - program stoi
    List<Callable<Integer>> tasks3 = new ArrayList<>();
    tasks3.add(task1);
    tasks3.add(task2);
    // List<Future<Integer>> results3 = exec.invokeAll(tasks3); //bez TIMEOUT
    List<Future<Integer>> results3 = exec.invokeAll(tasks3, 1, TimeUnit.SECONDS); // z TIMEOUT
    System.out.println("Wykonano " + " iteracji");
    // Oddaje stan powodzenia poszczególnych wątków
    // results3.get(...).isCancelled() - odda true dla tych, które zostały przerwane
    // results3.get(...).get() - da błąd dla tych, które zostały przerwane
  }

  // test na współdzielenie przy pomocy "volatile". Można też wykorzystać STATIC i
  // FINALLY
  public static void ShareTest1() {
    Runnable hellos = () -> {
      for (int i = 1; i <= 1000; i++)
        System.out.println("Hello " + i);
      done = true;
    };
    Runnable goodbye = () -> {
      int i = 1;
      while (!done)
        i++;
      System.out.println("Goodbye " + i);
      System.out.println("Koniec goodbye");
    };
    Executor executor = Executors.newCachedThreadPool();
    executor.execute(hellos);
    executor.execute(goodbye);
  }

  // strumienie parallel oddają wyniki w odpowiedniej kolejności
  public static void ParrarelStream() {
    String str1 = "01234567890123456789";
    System.out.println("I:" + str1);
    System.out.print("S:");
    String res1 = str1.chars().parallel().filter(s -> s > 0).mapToObj(s -> String.valueOf(((char) s)))
        .peek(s -> System.out.print(" " + s)).collect(Collectors.joining(" "));
    System.out.println("\nR: " + res1);
  }

  // Wypełnienie tablicy (ma zadany rozmiar) wartościami
  public static void ParrarelArraysFill() {
    String[] vals1 = { "ala", "ola", "ela" };
    Integer[] vals2 = new Integer[8];// ();
    Arrays.parallelSetAll(vals1, i -> "'" + String.valueOf(i % 10) + "'");
    Arrays.parallelSetAll(vals2, i -> (i % 10));
    System.err.println(Arrays.asList(vals1));
    System.err.println(Arrays.asList(vals2));
  }

  // Sortowanie tablicy
  public static void ParrarelArraysSort() {
    String[] vals1 = { "ala", "ola", "ela" };
    Arrays.parallelSort(vals1);
    System.err.println(Arrays.asList(vals1));
  }

  // Wątkowo bezpieczna mapa
  // wydajność: map can efficiently support a large number of concurrent readers
  // and a certain number of concurrent writers
  private static volatile ConcurrentHashMap<String, Integer> concMap = new ConcurrentHashMap<String, Integer>();
  static Callable<Integer> task31 = () -> {
    int counter = (new Random()).nextInt(5) + 5;
    int count = 0;
    System.out.println("Task31: " + counter);
    for (int i = 0; i < counter; i++) {
      Integer oldValue = concMap.get("Task3");
      Integer newValue = oldValue == null ? 1 : oldValue + 1;
      // concMap.put("Task3", newValue); // Error—might not replace oldValue
      concMap.compute("Task3", (k, v) -> v == null ? 1 : v + 1);
      count++;
      Thread.sleep(100);
    }
    System.out.println("Koniec Task31");
    return count;
  };

  static Callable<Integer> task32 = () -> {
    int counter = (new Random()).nextInt(5) + 5;
    int count = 0;
    System.out.println("Task32: " + counter);
    for (int i = 0; i < counter; i++) {
      Integer oldValue = concMap.get("Task3");
      Integer newValue = oldValue == null ? 1 : oldValue + 1;
      // concMap.put("Task3", newValue); // Error—might not replace oldValue
      concMap.compute("Task3", (k, v) -> v == null ? 1 : v + 1);
      count++;
      Thread.sleep(100);
    }
    System.out.println("Koniec Task32");
    return count;
  };

  static Callable<Integer> task4 = () -> {
    int counter = (new Random()).nextInt(5) + 5;
    System.out.println("Task4: " + counter);
    for (int i = 0; i < counter; i++) {
      Integer oldValue = concMap.get("Task4");
      Integer newValue = oldValue == null ? 1 : oldValue + 1;
      // concMap.put("Task4", newValue); // Error—might not replace oldValue
      concMap.compute("Task4", (k, v) -> v == null ? 1 : v + 1);
      Thread.sleep(50);
    }
    return counter;
  };

  public static void ConcurrentHashMap() throws Exception, InterruptedException, ExecutionException {
    ExecutorService exec = Executors.newCachedThreadPool();

    // Oczekiwanie na wynik pojedynczego wątku - program dalej działa i można
    // kontrolować wątek
    List<Callable<Integer>> tasks = new ArrayList<>();
    tasks.add(task31);
    tasks.add(task4);
    tasks.add(task32);
    tasks.add(task4);
    List<Future<Integer>> results = exec.invokeAll(tasks);
    exec.shutdown();
    System.out.println("Raport zbiorczy: " + concMap);
  }

  // Kolejka parallel in/out
  // Stosować gdy mało wątkow wypeminia, ale wiele musi opracowywać (opracowanie
  // jest bardziej pracochłonne niż wypełnianie)
  public static volatile ArrayBlockingQueue arrQueue = new ArrayBlockingQueue(3);
  static Callable<Integer> taskConsumer = () -> {
    Integer in;
    try {
      do {
        in = (Integer) arrQueue.poll();
        // q.poll(100, TimeUnit.MILLISECONDS); //próba odczytania będzie trwała przez
        // 100 ms, po tym czasie jeśli kolujka wciąz będzzie pusta to odda null
        System.out.println("Odebrano " + in + " (" + Thread.currentThread().getId() + ")");
        Thread.sleep(400);
      } while (in == null || in != -1);
      arrQueue.put(new Integer(-1));
    } catch (InterruptedException ignored) {
    }
    return -2;
  };

  public static void RunQueue() throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.submit(() -> {
      try {
        for (int i = 0; i < 15; i++) {
          System.out.println("Włożono " + i);
          arrQueue.put(new Integer(i));
          Thread.sleep(100);
        }
        arrQueue.put(new Integer(-1));
      } catch (InterruptedException ignored) {
      }
    });
    List<Callable<Integer>> tasks = new ArrayList<>();
    tasks.add(taskConsumer);
    tasks.add(taskConsumer);
    tasks.add(taskConsumer);
    ExecutorService exec2 = Executors.newCachedThreadPool();
    List<Future<Integer>> results = exec2.invokeAll(tasks);
    exec2.shutdown();
    exec.shutdown();
  }

  // ConcurrentSkipListMap - używać jeśli zachodzi potrzeba na przechodzenie po
  // posortowanych kluczach
  // ConcurrentSkipListSet - jeśli potrzena metod z NavigableMap
  // NIE MA ConcurrentHashSet - patrz str. 320

  // Atoimic - sposób na incrementatory wątkobezpieczne
  public static AtomicInteger nextNumber = new AtomicInteger();

  public static void AtomicTest() {
    // zwiększa licznik o jeden
    nextNumber.incrementAndGet();

    // ustawienie konkretnej wartości
    int lp = 7;
    // ŹLE
    nextNumber.set(Math.max(nextNumber.get(), lp));
    // dobrze
    nextNumber.updateAndGet(x -> Math.max(x, lp));
    // dobrze
    nextNumber.accumulateAndGet(lp, Math::max);

    // robi to co poprzednio, ale dodatkowo zwraca klucz Z PRZED zmiany
    Integer oldLp;
    oldLp = nextNumber.getAndUpdate(x -> Math.max(x, lp));
    oldLp = nextNumber.getAndAccumulate(lp, Math::max);

    // Gdy wiele wątków jednocześnie chce dostępu do generatora mogą pojawić się
    // problemy z wydajnością
    // Nalezy zastosować
    LongAdder la = new LongAdder();
    la.increment();// zwiększa licznik
    la.decrement();// zmniejsza licznik
    la.add(7);// zwiększa licznik o 7
    Long newLp = la.sum();// oddaje aktualną wartość
    // Problemem jest, że increment nie odda wartości, więc średnio to nadaje się do
    // generowania id, bo te trzeba odczytać.
    // Można za to wykorzystać to jako sumator dla wielu równolegle działających
    // wątków.

    // LongAccumulator - to generalizacja LongAdder. Do konstruktora sam podajesz
    // sposób zwiększania wartości i wartość startową
    // DoubleAdder - odpowiednik LongAdder - nie ma incremenmt
    // DoubleAccumulator - odpowiednik LongAccumulator
    DoubleAdder da = new DoubleAdder();
    da.add(7.4);
    Double newLp2 = da.sum();

    // Poniższe computeIfAbsent pozwala na zawsze uzyskanie wartość - oddaje gry w
    // mapie nie ma key
    ConcurrentHashMap<String, LongAdder> counts = new ConcurrentHashMap<String, LongAdder>();
    counts.computeIfAbsent("key", k -> new LongAdder()) // zapewnia obezność elementu
        .increment(); // wykonuje działanie na elemencie
  }

  // Lock - blokada sekcji krytycznej - raczej używać synchronized ()
  static volatile Lock countLock = new ReentrantLock();

  public static void Locks() throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();
    Runnable task = () -> {
      try {
        System.out.println("Próbuję dostaćsię przez blokadę (" + Thread.currentThread().getId() + ")");
        countLock.lock();
        System.out.println("Udało się! Blokuje dostęp na 5 sec (" + Thread.currentThread().getId() + ")");
        Thread.sleep(2000);
        System.out.println("Zwalniam blokadę (" + Thread.currentThread().getId() + ")");
        countLock.unlock();
      } catch (InterruptedException ignored) {
      }
    };
    exec.submit(task);
    Thread.sleep(50);
    exec.submit(task);
    exec.shutdown();
  }

  // Blokowanie bloku komend
  public static void SyncTest1() throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();
    Runnable task = () -> {
      try {
        System.out.println("Próbuję dostać się przez blokadę (" + Thread.currentThread().getId() + ")");
        synchronized (countLock) {
          System.out.println("Udało się! Blokuje dostęp na 2 sec (" + Thread.currentThread().getId() + ")");
          Thread.sleep(2000);
          System.out.println("Zwalniam blokadę (" + Thread.currentThread().getId() + ")");
        }
      } catch (InterruptedException ignored) {
      }
    };
    exec.submit(task);
    Thread.sleep(50);
    exec.submit(task);
    exec.shutdown();
  }

  // Metoda - blokowanie jej wywołania
  public static synchronized void SyncFunc() throws InterruptedException {
    System.out.println("Udało się! Blokuje dostęp na 2 sec (" + Thread.currentThread().getId() + ")");
    Thread.sleep(2000);
    System.out.println("Zwalniam blokadę (" + Thread.currentThread().getId() + ")");
  }

  public static void SyncTest2() throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();
    Runnable task = () -> {
      try {
        System.out.println("Próbuję dostać się przez blokadę (" + Thread.currentThread().getId() + ")");
        SyncFunc();
      } catch (InterruptedException ignored) {
      }
    };
    exec.submit(task);
    Thread.sleep(50);
    exec.submit(task);
    exec.shutdown();
  }

  // Przykład countera wykorzystywanego przez wiele wątków
  public static void ParallelCounter() throws InterruptedException {
    int threads = 2000;
    ExecutorService executor = Executors.newFixedThreadPool(threads);
    final AtomicInteger atomi = new AtomicInteger();
    for (int i = 0; i < threads; i++) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          atomi.getAndIncrement();
        }
      });
    }
    executor.awaitTermination(1, TimeUnit.SECONDS);
    executor.shutdown();
    System.out.println(atomi.get());
  }

  // Zasięg synchronized
  // Każdy obiekt posiada wewnętrzny lock, więc jeśli:
  // - klasa na kilka metod ustawionych jako synchronized
  // - używa wewnątrz synchronized(this){...}
  // to wszystkie te akcje będa blokowac się jednocześnie
  public static void Wait1() throws InterruptedException {
    Queue q = new Queue();

    ExecutorService exec = Executors.newCachedThreadPool();
    Runnable taskAdd = () -> {
      for (int i = 0; i < 10; i++) {
        Object o = new Object();
        System.out.println("(1) Próbuję dodać obiekt " + o.hashCode());
        q.add(new Object());
        System.out.println("(1) Obiekt dodany");
        try {
          Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    Runnable taskGet = () -> {
      System.out.println("Próbuję pobrać obiekt");
      try {
        Object o = q.get();
        System.out.println("Pobrałem obiekt " + o.hashCode());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    };

    Queue q2 = new Queue();
    Runnable taskAdd2 = () -> {
      for (int i = 0; i < 10; i++) {
        Object o = new Object();
        System.out.println("(2) Próbuję dodać obiekt " + o.hashCode());
        q2.add(new Object());
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    // taskGet zablokuje taskAdd, ale nie taskAdd2
    exec.submit(taskGet);
    Thread.sleep(50);
    exec.submit(taskAdd);
    Thread.sleep(50);
    exec.submit(taskAdd2);
  }

  // Rozwiązanie problemu z Wait1
  //
  public static void Wait2() throws InterruptedException {
    Queue q = new Queue();

    ExecutorService exec = Executors.newCachedThreadPool();
    Runnable taskAdd = () -> {
      for (int i = 0; i < 10; i++) {
        Object o = new Object();
        System.out.println("(2) Próbuję dodać obiekt " + o.hashCode());
        q.add2(new Object()); // Zmodyfikowana wersja budząca wszystkich posiadaczy tej samej blokady
        System.out.println("(2) Obiekt dodany");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    Runnable taskGet = () -> {
      for (int i = 0; i < 10; i++) {
        System.out.println("(2) Próbuję pobrać obiekt");
        try {
          Object o = q.get2(); // zmodyfikowana wersja, która usypia zamiast blokować
          System.out.println("(2) Pobrałem obiekt " + o.hashCode());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    };

    // taskGet zablokuje taskAdd, ale nie taskAdd2
    exec.submit(taskGet);
    Thread.sleep(50);
    exec.submit(taskAdd);
  }

  // Proste utworzenie wątka
  public static void ThreadSimple() throws InterruptedException {
    Runnable task = () -> {
      System.out.println("Task start.");
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Task end.");
    };
    Thread thread = new Thread(task);
    // thread.start(); //Rozpoczeyna pracę i idzie dalej
    thread.join(1000); // Rozpoczyna pracę i czeka na jej zakończenie 1sek. Jeśli się nie doczeka, to
                       // zabija wątek
    System.out.println("Thread zakończony");
  }

  // Przerywanie wątka przy pomocy Interrupt
  public static void ThreadStop() throws InterruptedException {
    Runnable taskCount = () -> {
      for (int i = 0; i < 10; i++) {
        if (Thread.currentThread().isInterrupted())// to wyłapie interrupt rzucony po sleep
          return;
        if (Thread.interrupted())
          return;
        System.out.println("Iteracja " + i);
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {// to wyłapie interrupt rzucony w czasie sleep
          System.out.println("Cought InterruptedException");
          e.printStackTrace();
          return;
        }
      }
      System.out.println("Task end.");
    };

    Thread thread2 = new Thread(taskCount);
    thread2.start();
    Thread.sleep(1200);
    thread2.interrupt();
  }

  // Różne właściwiści threadow
  public static void ThreadMisc() {
    // priorities
    // states: new, running, blocked on, input/output, waiting, or terminated
    // thread.setDaemon(true): When only daemon threads remain, the virtual machine
    // exits
  }

  // UI vs długo trwające zadania
  public static void ThreadLong() {
    // brak jakiegokolwiek opisu aczkowiek wynika, że należy petrzeć na konkretny UI
    // (Swing, android, java FX)
  }

  public static CompletableFuture<Integer> task1(Integer loopCount) throws InterruptedException {
    for (int i = 0; i < loopCount.intValue(); i++) {
      System.out.println("Iteracja 1." + i);
      Thread.sleep(500);
    }
    CompletableFuture<Integer> res = new CompletableFuture<>();
    res.complete(loopCount - 1);
    return res;
  };

  public static CompletableFuture<String> task22(int loopCount) {
    for (int i = 0; i < loopCount/* .intValue() */; i++) {
      System.out.println("Iteracja 2." + i);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    CompletableFuture<String> res = new CompletableFuture<>();
    res.complete("Zakończono");
    return res;
  };

  public static CompletableFuture<String> task3(String info) {
    for (int i = 0; i < 3/* .intValue() */; i++) {
      System.out.println("Iteracja 3." + i);
      try {
        Thread.sleep(150);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    CompletableFuture<String> res = new CompletableFuture<>();
    // res.complete("Zakończono 3 z wynikiem '"+info+"'");
    System.out.println("Zakończono 3 z wynikiem '" + info + "'");
    return res;
  };

  // CompletableFuture pozwala na wywoływanie jednego po zakończeniu drugiego.
  // To jest prosty przykład, a jest tam o wiele więcej.
  // Na razie wystarczy, ale trzeba znalezć lepsze zródło
  public static void Chain() throws InterruptedException {
    // ver1
    CompletableFuture<Integer> res1 = task1(3);
    try {
      CompletableFuture<String> res2 = res1.thenApply((i) -> task22(i)).get();
      res2.thenAccept((s) -> task3(s));
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ;

    // ver2
    task1(3).thenApply((i) -> task22(i)).thenAccept((s) -> {
      try {
        task3(s.get());
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (ExecutionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });

    int brk;
    brk = 8;
  }

}
