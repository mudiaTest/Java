package jooq.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.jooq.Field;
import org.jooq.Record;
import org.modelmapper.ModelMapper;

public interface RecordStream<TT> extends Stream<TT>{
  
  /**
   * Grupowanie rekordów po wartości pola 
   */
  public <R> RecordStream<List<TT>> recordsGroup(Field<R> field);
  public <R> RecordStream<TT> recordsGroup2(Field<R> field);
  public <R> RecordStream<String> recordsGroup3(Field<R> field);
  
  /**
   * Grupowanie rekordów po wartości pola 
   */
  public <R> RecordStream<List<TT>> recordsGroup(Field[] fields);
  
  /**
   * Filtrowanie rekordów dla wartości pola
   */
  public <R> RecordStream<TT> recordsFilter(Field<R> field, R value);
  
  /**
   * Filtrowanie rekordów dla pól o tych samych wartościach
   */
  public <R,S> RecordStream<TT> recordsFilter(Field[][] fieldPairs);
  
  /**
   * Filtrowanie rekordów dla pól o tych samych wartościach
   */
  public <R> RecordStream<TT> recordsFilter(Field<R> field1, Field<R> field2);
  
  /**
   * Distinct po field - grupuje w poartościach wybranego field i zwraca pierwszy z każdej grupy
   */
  public <R> RecordStream<TT> recordsDistinct(Field<R> field);
  
  /**
   * Distinct po field - grupuje w poartościach wybranego field i zwraca pierwszy z każdej grupy
   */
  public <R> RecordStream<TT> recordsDistinct(Field[] fields);
  
  /**
   * Mapowanie rekordów na obiekty zadanej klasy
   */
  public <C> RecordStream<C> objectsMap(ModelMapper mapper, Class<C> clazz);
  
  /**
   * Distinct rekordów po fields i następnie mapowanie ich na obiekty zadanej klasy
   */
  public <R,C> RecordStream<C> objectsDistinctMap(Field[] fields, ModelMapper mapper, Class<C> clazz);
  
  /**
   * 
   * @param lst
   * @param fields
   * @param mapper
   * @param clazz
   * @return imput stream
   * Jeśli "fields" jest != null, to robi distinct po liście "fields".
   * Następnie wypełnia "lst" obiektami zmapowanymi za pomocą "mapper" do klasy "clazz".
   */
//  public <R,C> RecordStream<TT> mapRecordsToObjectList(List<C> lst, ExtModelMapper emm);
  //public <R,C> RecordStream<Pair<C,List<TT>>> mapRecordsToMasterObjectList(List<C> lst, ExtModelMapper emm);
  public <R,C> RecordStream<Pair<C,Collection<TT>>> mapRecordsToObjectCollection(Collection<C> lst, ExtModelMapper emm);
  public <R,C,D> RecordStream<Pair<D,Collection<TT>>> mapRecordsToSubObjectCollection(String collectionName, ExtModelMapper emm);
  
  
  
  /*--------------------------*/
  
  
  @Override
  public boolean allMatch(Predicate<? super TT> arg0);

  @Override
  public boolean anyMatch(Predicate<? super TT> arg0);

  @Override
  public <R, A> R collect(Collector<? super TT, A, R> arg0) ;

  @Override
  public <R> R collect(Supplier<R> arg0, BiConsumer<R, ? super TT> arg1, BiConsumer<R, R> arg2);

  @Override
  public long count() ;

  @Override
  public RecordStream<TT> distinct() ;

  @Override
  public RecordStream<TT> dropWhile(Predicate<? super TT> arg0) ;

  @Override
  public RecordStream<TT> filter(Predicate<? super TT> arg0);

  @Override
  public Optional<TT> findAny() ;

  @Override
  public Optional<TT> findFirst() ;

  @Override
  public <R> RecordStream<R> flatMap(Function<? super TT, ? extends Stream<? extends R>> arg0) ;

  @Override
  public DoubleStream flatMapToDouble(Function<? super TT, ? extends DoubleStream> arg0);

  @Override
  public IntStream flatMapToInt(Function<? super TT, ? extends IntStream> arg0);

  @Override
  public LongStream flatMapToLong(Function<? super TT, ? extends LongStream> arg0);

  @Override
  public void forEach(Consumer<? super TT> arg0);

  @Override
  public void forEachOrdered(Consumer<? super TT> arg0) ;

  @Override
  public RecordStream<TT> limit(long arg0) ;

  @Override
  public <R> RecordStream<R> map(Function<? super TT, ? extends R> arg0) ;

  @Override
  public DoubleStream mapToDouble(ToDoubleFunction<? super TT> arg0);

  @Override
  public IntStream mapToInt(ToIntFunction<? super TT> arg0);

  @Override
  public LongStream mapToLong(ToLongFunction<? super TT> arg0);

  @Override
  public Optional<TT> max(Comparator<? super TT> arg0) ;

  @Override
  public Optional<TT> min(Comparator<? super TT> arg0) ;

  @Override
  public boolean noneMatch(Predicate<? super TT> arg0) ;

  @Override
  public RecordStream<TT> peek(Consumer<? super TT> arg0);

  @Override
  public Optional<TT> reduce(BinaryOperator<TT> arg0);

  @Override
  public TT reduce(TT arg0, BinaryOperator<TT> arg1) ;

  @Override
  public <U> U reduce(U arg0, BiFunction<U, ? super TT, U> arg1, BinaryOperator<U> arg2) ;

  @Override
  public RecordStream<TT> skip(long arg0) ;

  @Override
  public RecordStream<TT> sorted();

  @Override
  public RecordStream<TT> sorted(Comparator<? super TT> arg0) ;

  @Override
  public RecordStream<TT> takeWhile(Predicate<? super TT> arg0);

  @Override
  public Object[] toArray();

  @Override
  public <A> A[] toArray(IntFunction<A[]> arg0);

  @Override
  public void close() ;

  @Override
  public boolean isParallel();

  @Override
  public Iterator<TT> iterator() ;

  @Override
  public RecordStream<TT> onClose(Runnable arg0) ;

  @Override
  public RecordStream<TT> parallel() ;

  @Override
  public RecordStream<TT> sequential() ;

  @Override
  public Spliterator<TT> spliterator() ;

  @Override
  public RecordStream<TT> unordered() ;
}
