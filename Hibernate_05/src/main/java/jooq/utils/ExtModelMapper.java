package jooq.utils;

import org.jooq.Field;
import org.modelmapper.ModelMapper;

public class ExtModelMapper<C> extends ModelMapper{
  public Class<C> clazz;
  ModelMapper mapper;
  Field[] fields;
  
  public ExtModelMapper(Class<C> clazz, ModelMapper mapper, Field... fields){
    this.clazz = clazz;
    this.mapper = mapper;
    this.fields = fields;
  }
  
  public ExtModelMapper(Class<C> clazz, ModelMapper mapper){
    this.clazz = clazz;
    this.mapper = mapper;
  }
}
