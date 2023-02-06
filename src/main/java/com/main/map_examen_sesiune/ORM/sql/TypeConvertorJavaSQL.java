package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.exceptions.TypeConversionFailedException;

import java.util.HashMap;
import java.util.Map;

public class TypeConvertorJavaSQL {
    // Java     SQL
    static Map<String, String> dict = null;
    private static void init(){
        dict = new HashMap<>();
        // dict.put("char", "CHAR"); type doesnt work use Character
        dict.put("Character", "CHAR");
        dict.put("long", "BIGINT");
        dict.put("Long", "BIGINT");
        dict.put("short", "SMALLINT");
        dict.put("Short", "SMALLINT");
        dict.put("int", "INT");
        dict.put("Integer", "INT");
        dict.put("String", "VARCHAR");
        dict.put("string", "VARCHAR");
        dict.put("boolean", "BOOLEAN");
        dict.put("double", "DECIMAL");
        dict.put("float", "DECIMAL");
        dict.put("Double", "DECIMAL");
        dict.put("Float", "DECIMAL");
        dict.put("LocalDateTime", "TIMESTAMP");
        dict.put("LocalDate", "DATE");
        dict.put("LocalTime", "TIME");
    }


    public static String getSQLType(String javaType) throws TypeConversionFailedException {
        if(dict == null)
            init();
        try {
            return dict.get(javaType);
        }
        catch (Exception ex){
            throw new TypeConversionFailedException("Could not convert "+javaType+" to SQL");
        }
    }
}