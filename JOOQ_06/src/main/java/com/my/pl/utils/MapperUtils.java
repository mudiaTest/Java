package com.my.pl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jooq.Field;
import org.jooq.Record;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Component;

@Component
public class MapperUtils {

  /*
   * Grupowanie rekordów po wartości pola 
   */
  public <R> Map<R, List<Record>> recordsByField(Iterable<Record> src, Field<R> field){
    Map<R, List<Record>> result = StreamSupport.stream(src.spliterator(), false)
      .collect(Collectors.groupingBy( a -> a.get(field)));
    return result;
  }
  
  /*
   * Filtrowanie rekordów dla wartości pola
   */
  public <R> List<Record> recordsByFieldValue(Iterable<Record> src, Field<R> field, R value){
    List<Record> result = StreamSupport.stream(src.spliterator(), false)
      .filter(rec -> rec.getValue(field).equals(value))
      .collect(Collectors.toList());
    return result;
  }
  
  /*
   * Mapowanie rekordów na obiekty zadanej klasy
   */
  public <R,C> List<C> objectsByMapper(Iterable<Record> src, ModelMapper mapper, Class<C> clazz){            
    List<C> result = new ArrayList<>();
    StreamSupport.stream(src.spliterator(), false).forEach( rec -> result.add(mapper.map(rec, clazz)) );
    return result;
  }
  
  
  public <R> Map<R, List<Record>> mapFilterRecordsByFieldPairsByKeyField(Iterable<Record> src, Field<R> keyField, Field<R>[][] fieldChain){    
    return recordsByField(listFilterRecordsByFieldPairs(src, fieldChain), keyField);      
  }

  public <R> List<Record> listFirstRecordsByPKField(Iterable<Record> src, Field<R> f){
    return recordsByField(src, f).values().stream().map(a -> a.get(0)).collect(Collectors.toList());
  }
  
  public <K,C> List<C> objectListRecordsByPK(Iterable<Record> src, Field<K> pk, ModelMapper mapper, Class<C> clazz){
    List<C> result = new ArrayList<>();
    listFirstRecordsByPKField(src, pk).stream().forEach( a -> result.add(mapper.map(a, clazz)) );
    return result;
  }
  
  public <K,C> List<C> objectListRecordsByPKFilter(Iterable<Record> src, Field<K> pk, ModelMapper mapper, Class<C> clazz){
    List<C> result = new ArrayList<>();
    listFirstRecordsByPKField(src, pk).stream().forEach( a -> result.add(mapper.map(a, clazz)) );
    return result;
  }
    
  public <R> List<Record> recordListRecordsByFieldValue(Iterable<Record> src, Field<R> f, R key){
    return recordsByField(src, f).get(key);
  }

  public <R,C> List<C> objectListRecordsByPK(Iterable<Record> src, Field<R> f, ModelMapper mapper, Class<C> clazz, R pk){      
    List<Record> src2 = recordListRecordsByFieldValue(src, f, pk);
    List<C> result = objectListRecordsByPK(src2, f, mapper, clazz);    
    return result;
  }
  /*
  public <R,C> List<C> listRecordsByFieldValueToObjects(Iterable<Record> src, Field<R> pkField, Field<R>[][] fieldChain, ModelMapper mapper, Class<C> clazz){      
    List<Record> src2 = listFilterRecordsByFieldPairs(src, pkField, fieldChain);
    List<C> result = mapRecordsToObjects(src2, pkField, mapper, clazz);    
    return result;
  }*/
  
  public <R,C> List<C> listRecordsByPKToObjects(Map<R, List<Record>> src, Field<R> f, ModelMapper mapper, Class<C> clazz, R pk){      
    List<Record> src2 = recordListRecordsByFieldValue(src.get(pk), f, pk);
    List<C> result = objectListRecordsByPK(src2, f, mapper, clazz);    
    return result;
  }
    
  public <R,K> List<Record> listFilterRecordsByFieldPairs(Iterable<Record> src, Field<R>[][] fieldChain){          
    Stream<Record> s = StreamSupport.stream(src.spliterator(), false);
    
    List<Record> result = StreamSupport.stream(src.spliterator(), false)
      .filter( r -> {
        Boolean satisfy = true;
        for(int i=0; i<fieldChain.length-1; i++) 
          satisfy = satisfy && r.getValue(fieldChain[i][0]).equals(r.getValue(fieldChain[i][1]));        
        return satisfy;
      })
      .collect(Collectors.toList());
    return result;
  }
  
  public ModelMapper mmByPrefix(String prefix) {
    ModelMapper result = new ModelMapper();
    result.getConfiguration().addValueReader(new PrefixValueReader(prefix));
    result.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
    return result;
  }
  
  public RecordStream<Record> recStream(Collection<Record> c){
    return new RecordStreamImpl<Record>(c.stream());
  }
}
