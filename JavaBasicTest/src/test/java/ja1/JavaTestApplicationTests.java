package ja1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaBasicTestApplicationTests {

  @Test
  void contextLoads() {
  }

  class ImplPredicateInteger implements Predicate<Integer> {
    @Override
    public boolean test(Integer t) {
      // TODO Auto-generated method stub
      return false;
    }
  }

  class ImplPredicateT<T> implements Predicate<T> {
    @Override
    public boolean test(T arg0) {
      // TODO Auto-generated method stub
      return false;
    }
  }

  public <T extends List<Object>> void genericTest1(T t) {

  }

  public void genericTest2(List<? extends Object> t) {

  }

//
  Function<Integer, Integer> funct = i -> i++;

  public void funcIntTest1(Function<Integer, Integer> f, Integer a, Integer b) {
    b = f.apply(a);
  }

  public void funcIntTest2(CustomFunctionalInterface<String> f) {
    String dst = f.change("src");
  }

  @Test
  public void dummy() {
    List<String> l = new ArrayList<>();
    funcIntTest1(i -> i++, 1, 2);
  }
}
