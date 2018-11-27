package com.my.pl.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
		return new RecordStreamImpl<TT>(stream.filter(arg0));
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
	public <R> RecordStream<List<TT>> recordsGroupedByField(RecordStream<TT> src, Field<R> field) {
		Stream<List<TT>> r = 
				src
				.collect(Collectors.groupingBy( rec -> ((Record) rec).get(field)))
				.values().stream();
		
		return new RecordStreamImpl<List<TT>>(r);
		//return null;
	}

	@Override
	public <R> RecordStream<TT> recordsByFieldValue(RecordStream<TT> src, Field<R> field, R value) {
		Stream<TT> r = src
			.filter(rec -> ((Record) rec).getValue(field).equals(value));
		return new RecordStreamImpl<TT>(r);
	}

	@Override
	public <C> RecordStream<C> objectsByMapper(RecordStream<TT> src, ModelMapper mapper, Class<C> clazz) {
		Stream<C> r = src.map(rec -> mapper.map(rec, clazz));
		return new RecordStreamImpl<C>(r);
	}
	
	
}
