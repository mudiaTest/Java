package hibernate01;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

  /*
   * Wrapper obudowuj�cy dane dzia�anie transakcj�.
   * Aby wykona� persist mmusi by� transakcja, kt�ra w Springu jest ustalana przez @Transaction.
   * @Transaction dzia�a tylko dla metod prywatnych, wi�c takie dzia�anie oblekamy we wrappera.
   */

@Service
public class TransactionWrapper {
  @Transactional
  public <T> T withTransaction(Supplier<T> supplier) {
    return supplier.get();
  }
  @Transactional
  public void withTransaction(Runnable runnable) {
    runnable.run();
  }
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public <T> T withNewTransaction(Supplier<T> supplier) {
    return supplier.get();
  }
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void withNewTransaction(Runnable runnable) {
    runnable.run();
  }
}