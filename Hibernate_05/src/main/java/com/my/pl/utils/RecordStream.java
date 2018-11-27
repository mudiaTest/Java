package com.my.pl.utils;

import java.util.List;
import java.util.stream.Stream;

import org.jooq.Field;
import org.jooq.Record;
import org.modelmapper.ModelMapper;

public interface RecordStream<TT> extends Stream<TT>{
	
	public RecordStream(Stream<TT> stream);
	
	/*
	 * Grupowanie rekordów po wartości pola 
	 */
	public <R> RecordStream<List<TT>> recordsGroupedByField(RecordStream<TT> src, Field<R> field);
	
	/*
	 * Filtrowanie rekordów dla wartości pola
	 */
	public <R> RecordStream<TT> recordsByFieldValue(RecordStream<TT> src, Field<R> field, R value);
	
	/*
	 * Mapowanie rekordów na obiekty zadanej klasy
	 */
	public <C> RecordStream<C> objectsByMapper(RecordStream<TT> src, ModelMapper mapper, Class<C> clazz);
}
