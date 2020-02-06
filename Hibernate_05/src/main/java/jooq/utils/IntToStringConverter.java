package com.my.pl.utils;

import org.jooq.Converter;

public class IntToStringConverter implements Converter<Integer, String> {
	@Override
    public String from(Integer val) {
		switch (val%2){
		case 0 :
			return "parzyste";		
		default: 
			return "nieparzyste";
		}		
    }

    @Override
    public Integer to(String str) {
    	if (str.equals("parzyste"))
			return 0;	
		else
			return 1;
    }

    @Override
    public Class<Integer> fromType() {
        return Integer.class;
    }

    @Override
    public Class<String> toType() {
        return String.class;
    }
}
