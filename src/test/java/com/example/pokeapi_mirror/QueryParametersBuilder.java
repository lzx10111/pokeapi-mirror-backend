package com.example.pokeapi_mirror;

import java.lang.reflect.Field;

public final class QueryParametersBuilder {
    private QueryParametersBuilder() {
    }

    public static String fromObject(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Field[] list = obj.getClass().getDeclaredFields();
        String query = "";

        for (Field f : list) {
            f.setAccessible(true);
            query = query.concat(f.getName()).concat("=").concat(String.valueOf(f.get(obj))).concat("&");
        }

        query = query.substring(0, query.length() - 1);

        return query;
    }

    public static String fromObjectArray(Object[] obj) throws IllegalArgumentException, IllegalAccessException {
        String query = "";
        Integer i = 0;
        for (Object o : obj) {
            if (i==0) {
                query = query.concat(fromObject(o));
            }
            
            if (i!=0) {
                query = query.concat("&").concat(fromObject(o));
            }

            i += 1;
        }

        return query;
    }
}
