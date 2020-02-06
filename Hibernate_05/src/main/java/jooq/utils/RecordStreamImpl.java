package jooq.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.util.Strings;
import org.javatuples.Pair;
import org.jooq.Field;
import org.jooq.Record;
import org.modelmapper.ModelMapper;

public class RecordStreamImpl<TT> implements RecordStream<TT>{

	private final Stream<TT> stream;
	
	public RecordStreamImpl(Stream<TT> stream){
        this.stream = stream;
    }

	@Override
	public boolean allMatch(Predicate<? super TT> arg0) {
		return stream.allMatch(arg0);
	}

	@Override
	public boolean anyMatch(Predicate<? super TT> arg0) {
		return stream.anyMatch(arg0);
	}

	@Override
	public <R, A> R collect(Collector<? super TT, A, R> arg0) {
		return stream.collect(arg0);
	}

	@Override
	public <R> R collect(Supplier<R> arg0, BiConsumer<R, ? super TT> arg1, BiConsumer<R, R> arg2) {
		return stream.collect(arg0, arg1, arg2);
	}

	@Override
	public long count() {
		return stream.count();
	}

	@Override
	public RecordStream<TT> distinct() {
		return new RecordStreamImpl<TT>(stream.distinct());
	}

	@Override
	public RecordStream<TT> dropWhile(Predicate<? super TT> arg0) {
		return new RecordStreamImpl<TT>(stream.dropWhile(arg0));
	}

	@Override
	public RecordStream<TT> filter(Predicate<? super TT> arg0) {
		RecordStreamImpl<TT> r = new RecordStreamImpl<TT>(stream.filter(arg0));
		return r;
	}

	@Override
	public Optional<TT> findAny() {
		return stream.findAny();
	}

	@Override
	public Optional<TT> findFirst() {
		return stream.findFirst();
	}

	@Override
	public <R> RecordStream<R> flatMap(Function<? super TT, ? extends Stream<? extends R>> arg0) {
		return new RecordStreamImpl<R>(stream.flatMap(arg0));
	}

	@Override
	public DoubleStream flatMapToDouble(Function<? super TT, ? extends DoubleStream> arg0) {
		return stream.flatMapToDouble(arg0);
	}

	@Override
	public IntStream flatMapToInt(Function<? super TT, ? extends IntStream> arg0) {
		return stream.flatMapToInt(arg0);
	}

	@Override
	public LongStream flatMapToLong(Function<? super TT, ? extends LongStream> arg0) {
		return stream.flatMapToLong(arg0);
	}

	@Override
	public void forEach(Consumer<? super TT> arg0) {
		
	}

	@Override
	public void forEachOrdered(Consumer<? super TT> arg0) {
		stream.forEach(arg0);
	}

	@Override
	public RecordStream<TT> limit(long arg0) {
		return new RecordStreamImpl<TT>(stream.limit(arg0));
	}

	@Override
	public <R> RecordStream<R> map(Function<? super TT, ? extends R> arg0) {
		return new RecordStreamImpl<R>(stream.map(arg0));
	}

	@Override
	public DoubleStream mapToDouble(ToDoubleFunction<? super TT> arg0) {
		return stream.mapToDouble(arg0);
	}

	@Override
	public IntStream mapToInt(ToIntFunction<? super TT> arg0) {
		return stream.mapToInt(arg0);
	}

	@Override
	public LongStream mapToLong(ToLongFunction<? super TT> arg0) {
		return stream.mapToLong(arg0);
	}

	@Override
	public Optional<TT> max(Comparator<? super TT> arg0) {
		return stream.max(arg0);
	}

	@Override
	public Optional<TT> min(Comparator<? super TT> arg0) {
		return stream.min(arg0);
	}

	@Override
	public boolean noneMatch(Predicate<? super TT> arg0) {
		return stream.noneMatch(arg0);
	}

	@Override
	public RecordStream<TT> peek(Consumer<? super TT> arg0) {
		return new RecordStreamImpl<TT>(stream.peek(arg0));
	}

	@Override
	public Optional<TT> reduce(BinaryOperator<TT> arg0) {
		return stream.reduce(arg0);
	}

	@Override
	public TT reduce(TT arg0, BinaryOperator<TT> arg1) {
		return stream.reduce(arg0,  arg1);
	}

	@Override
	public <U> U reduce(U arg0, BiFunction<U, ? super TT, U> arg1, BinaryOperator<U> arg2) {
		return stream.reduce(arg0, arg1, arg2);
	}

	@Override
	public RecordStream<TT> skip(long arg0) {
		return new RecordStreamImpl<TT>(stream.skip(arg0));
	}

	@Override
	public RecordStream<TT> sorted() {
		return new RecordStreamImpl<TT>(stream.sorted());
	}

	@Override
	public RecordStream<TT> sorted(Comparator<? super TT> arg0) {
		return new RecordStreamImpl<TT>(stream.sorted(arg0));
	}

	@Override
	public RecordStream<TT> takeWhile(Predicate<? super TT> arg0) {
		return new RecordStreamImpl<TT>(stream.takeWhile(arg0));
	}

	@Override
	public Object[] toArray() {
		return stream.toArray();
	}

	@Override
	public <A> A[] toArray(IntFunction<A[]> arg0) {
		return stream.toArray(arg0);
	}

	@Override
	public void close() {
		stream.close();		
	}

	@Override
	public boolean isParallel() {
		return stream.isParallel();
	}

	@Override
	public Iterator<TT> iterator() {
		return stream.iterator();
	}

	@Override
	public RecordStream<TT> onClose(Runnable arg0) {
		return new RecordStreamImpl<TT>(stream.onClose(arg0));
	}

	@Override
	public RecordStream<TT> parallel() {
		return new RecordStreamImpl<TT>(stream.parallel());
	}

	@Override
	public RecordStream<TT> sequential() {
		return new RecordStreamImpl<TT>(stream.sequential());
	}

	@Override
	public Spliterator<TT> spliterator() {
		return stream.spliterator();
	}

	@Override
	public RecordStream<TT> unordered() {
		return new RecordStreamImpl<TT>(stream.unordered());
	}

	
	/*
	 * tu nowe funkcje
	 */
	
	@Override
	public <R> RecordStreamImpl<List<TT>> recordsGroup(Field<R> field) {
		Stream<List<TT>> r = 
			stream
			.collect(Collectors.groupingBy( rec -> ((Record) rec).get(field)))
			.values().stream();
		RecordStreamImpl<List<TT>> result = new RecordStreamImpl<>(r);
		result.peek(a->{});
		return result;
		//return null;
	}
		public <R> RecordStreamImpl<TT> recordsGroup2(Field<R> field) {
			Stream<TT> r = 
					stream
					.collect(Collectors.groupingBy( rec -> ((Record) rec).get(field)))
					.values().stream().findFirst().get().stream();
			
			return new RecordStreamImpl<>(r);
			//return null;
		}
		public <R> RecordStream<String> recordsGroup3(Field<R> field) {
			List<String> l = new ArrayList<>();
			
			
			return new RecordStreamImpl<>(l.stream());
			//return null;
		}
	
		private String keyOfFields(Record rec, Field[] fields) {
			String result = "";
			for (Field field : fields)
				result = Strings.join((Arrays.asList(new String[] { result, ((Record) rec).get(field).toString()})).iterator(), '#');
			return result;
		}
	
	@Override
	public <R> RecordStreamImpl<List<TT>> recordsGroup(Field[] fields) {
		Stream<List<TT>> r = 
			stream
			.collect(Collectors.groupingBy( rec -> {
					return keyOfFields((Record)rec, fields);
				}					
				)
			)
			.values().stream();
		
		return new RecordStreamImpl<>(r);
		//return null;
	}

	@Override
	public <R> RecordStreamImpl<TT> recordsFilter(Field<R> field, R value) {
		Stream<TT> r = stream
			.filter(rec -> ((Record) rec).getValue(field).equals(value));
		return new RecordStreamImpl<TT>(r);
	}
	
	@Override
	public <R,S> RecordStreamImpl<TT> recordsFilter(Field[][] fieldPairs){
		Stream<TT> r = new RecordStreamImpl<TT>(stream)
			.filter(rec -> 
			{
				Boolean result = true;
				for (Field[] fieldPair : fieldPairs) 
					result = result && ((Record) rec).getValue(fieldPair[0]).equals(fieldPair[1]);
				return result;
			}
			);
		return new RecordStreamImpl<TT>(r);
	}
	
	@Override
	public <R> RecordStreamImpl<TT> recordsFilter(Field<R> field1, Field<R> field2){
		Stream<TT> r = new RecordStreamImpl<TT>(stream)
				.filter(rec -> ((Record) rec).get(field1).equals(((Record) rec).get(field2)));
		return new RecordStreamImpl<TT>(r);
	}
	
	@Override
	public <R> RecordStreamImpl<TT> recordsDistinct(Field<R> field){
		Stream<TT> r = new RecordStreamImpl<TT>(stream)
			.recordsGroup(field)
			.map(lst -> lst.get(0));
		return new RecordStreamImpl<TT>(r);
	}
	
	@Override
	public <R> RecordStreamImpl<TT> recordsDistinct(Field[] fields){
		Stream<TT> r = new RecordStreamImpl<TT>(stream)
			.recordsGroup(fields)
			.map(lst -> lst.get(0));
		return new RecordStreamImpl<TT>(r);
	}

	@Override
	public <C> RecordStreamImpl<C> objectsMap(ModelMapper mapper, Class<C> clazz) {
		Stream<C> r = stream.map(rec -> mapper.map(rec, clazz));
		return new RecordStreamImpl<C>(r);
	}
	
	@Override
	public <R,C> RecordStreamImpl<C> objectsDistinctMap(Field[] fields, ModelMapper mapper, Class<C> clazz){
		Stream<C> r = new RecordStreamImpl<TT>(stream)
			.recordsDistinct(fields)
			.objectsMap(mapper, clazz);
		return new RecordStreamImpl<C>(r);
	}
	
		private <C> void mapRecord(TT rec, List<C> lst, Field[] fields, ModelMapper mapper, Class<C> clazz, List<String> keyList){
			String key = "";
			if (fields!=null)
				key = keyOfFields((Record)rec, fields);
			if (fields==null || !keyList.contains(key)) {
				C obj = mapRecordToObj(rec, mapper, clazz);
				lst.add( obj);
				if (fields!=null)
					keyList.add(key);
			}
		}
		
		private <C> C mapRecordToObj(TT rec,ModelMapper mapper, Class<C> clazz){
			return mapper.map(rec, clazz);
		}
	
//	@Override
//	public <R,C> RecordStreamImpl<Pair<C,List<TT>>> mapRecordsToObjectCollection(List<C> lst, ExtModelMapper emm){
//		Field[] fields = emm.fields;
//		ModelMapper mapper = emm.mapper;
//		Class<C> clazz = emm.clazz;
//		
//		Map<String, Pair<C,List<TT>>> m = new HashMap<>(); 		
//		stream
//		.peek( rec -> {
//			String key = keyOfFields((Record)rec, fields);
//			if (!m.containsKey(key))
//			{
//				List<TT> l = new ArrayList<>() {{add(rec);}};
//				C obj = mapRecordToObj(rec, mapper, clazz);
//				m.put(key, new Pair(obj, l) );
//				lst.add(obj);
//			}
//			else
//				m.get(key).getValue1().add(rec);
//		})
//		.toArray();
//		return new RecordStreamImpl<Pair<C,List<TT>>>( m.values().stream() );
//	}
			
	@Override
	public <R,C> RecordStreamImpl<Pair<C,Collection<TT>>> mapRecordsToObjectCollection(Collection<C> lst, ExtModelMapper emm){
		Field[] fields = emm.fields;
		ModelMapper mapper = emm.mapper;
		Class<C> clazz = emm.clazz;
		
		Map<String, Pair<C,Collection<TT>>> m = new HashMap<>(); 		
		stream
		.peek( rec -> {
			String key = keyOfFields((Record)rec, fields);
			if (!m.containsKey(key)) {
				List<TT> l = new ArrayList<>() {{add(rec);}};
				C obj = mapRecordToObj(rec, mapper, clazz);
				m.put(key, new Pair(obj, l) );
				lst.add(obj);
			}
			else
				m.get(key).getValue1().add(rec);
		}).
		toArray();
		return new RecordStreamImpl<Pair<C,Collection<TT>>>( m.values().stream() );
	}
		
		public static java.lang.reflect.Field getField(String name, Class<?> type) {
			java.lang.reflect.Field field = null;
			try {
				field = type.getField(name);
			}
			catch(NoSuchFieldException e){
				if (type.getSuperclass() != null)
					field = getField(name, type.getSuperclass());
			}			
			return field;
		}
		
		public static java.lang.reflect.Method getMethod(String name, Class<?> type) {
			java.lang.reflect.Method method = null;
			try {
				method = type.getMethod(name);
			}
			catch(NoSuchMethodException e){
				if (type.getSuperclass() != null)
					method = getMethod(name, type.getSuperclass());
			}			
			return method;
		}		

		private <C> Collection<C> getCollection(String name, Object obj){
			/*
			 * Field to tylko reprezentacja (zbiór danych) i polu a nie samo pole
			 */
//			java.lang.reflect.Field f = null;
//			try {
////				java.lang.reflect.Field[] g = obj.getClass().getFields();
////				String n = g[0].getName();
//				f = obj.getClass().getDeclaredField(name);
//			} catch (NoSuchFieldException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			java.lang.reflect.Field f = null;
			try{
				f = getField(name, obj.getClass());
			}
			catch(SecurityException e){
				e.printStackTrace();
			}
			
			if ((f != null) && 
				/*
				 * Sprawdzanie, czy f implementuje Collection
				 */
				Collection.class.isAssignableFrom(f.getType()) && 
				/*
				 * Sprawdzanie, czy pole jest prywatne
				 */
				(!Modifier.isPrivate(f.getModifiers())) ){
				/*
				 * Field to tylko reprezentacja (zbiór danych) o polu a nie samo pole
				 */
				try {
					return (Collection<C>)f.get(obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}
			else 
			{
				java.lang.reflect.Method m = null;
				try{
					m = getMethod(name, obj.getClass());
				}
				catch(SecurityException e){
					e.printStackTrace();
				}
				if ((m != null) && 
					/*
					 * Sprawdzanie, czy f implementuje Collection
					 */
					Collection.class.isAssignableFrom(m.getReturnType()) && 
					/*
					 * Sprawdzanie, czy pole jest prywatne
					 */
					(!Modifier.isPrivate(m.getModifiers())) ){
						try {
							/*
							 * m to reprezentacja metody
							 * m.invoke(obj, x, y, ...) -> obj.m(x, y, ...)
							 */
							Object q = m.invoke(obj, null);
							Collection<C> w = (Collection<C>)m.invoke(obj, null);
							return w;
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}
				}
				else
					return null;
			}
		}
		
		public RecordStream<TT> recStream(Collection<TT> c){
			return new RecordStreamImpl<TT>(c.stream());
		}

	@Override
	public <R, C, D> RecordStreamImpl<Pair<D,Collection<TT>>> mapRecordsToSubObjectCollection(String collectionName,
			ExtModelMapper emm) {

		List<Pair<D,Collection<TT>>> lst = new ArrayList<>();
		stream
		.peek( pair -> {
			Pair<D, Collection<TT>> p = (Pair<D, Collection<TT>>)pair;
			Collection<C> c = getCollection(collectionName, p.getValue0());
			recStream(p.getValue1()).mapRecordsToObjectCollection(c, emm);
			lst.add(p);
		})
		.toArray()
		;
		//return new RecordStreamImpl<TT>(stream);
		return new RecordStreamImpl<Pair<D,Collection<TT>>>( lst.stream() );
	}

	
	
	
}
