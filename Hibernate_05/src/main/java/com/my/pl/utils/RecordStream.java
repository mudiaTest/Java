package com.my.pl.utils;

import java.util.List;
import java.util.stream.Stream;

import org.jooq.Field;
import org.jooq.Record;
import org.modelmapper.ModelMapper;

public interface RecordStream<TT> extends Stream<TT>{
	
	//public RecordStream(Stream<TT> stream);
	
	/**
	 * Grupowanie rekordów po wartości pola 
	 */
	public <R> RecordStream<List<TT>> recordsGroup(Field<R> field);
	public <R> RecordStream<TT> recordsGroup2(Field<R> field);
	
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
//	public <R,C> RecordStream<TT> mapRecordsToObjectList(List<C> lst, Field[] fields, ModelMapper mapper, Class<C> clazz);
	
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
	public <R,C> RecordStream<TT> mapRecordsToObjectList(List<C> lst, ExtModelMapper emm);
}
