package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;
import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.NullObjectException;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InsertEntityScript {
    public static String getScript(Object obj) throws OrmException, IllegalAccessException {
        StringBuilder script = new StringBuilder("INSERT INTO " + obj.getClass().getSimpleName().toLowerCase() + "(");
        ArrayList<Field> fieldsToGet = new ArrayList<>();
        for(Field f: FieldsParser.getAllFields(obj.getClass())){
            if(f.getAnnotation(PrimaryKey.class) != null){
                String tmp = primaryKey(f);
                if(!tmp.equals("")) {
                    script.append(primaryKey(f)).append(", ");
                    fieldsToGet.add(f);
                }
                continue;
            }
            script.append(f.getName().toLowerCase()).append(", ");
            fieldsToGet.add(f);
        }
        script.setLength(script.length()-2);
        script.append(") VALUES (");
        for(Object o: FieldsParser.getAllFieldsValues(obj, fieldsToGet)){
            scriptAddObject(script, o);
        }
        script.setLength(script.length()-2);
        script.append(")");
        return script.toString();
    }

    private static void scriptAddObject(StringBuilder script, Object o) {
        if(o.getClass().equals(LocalDateTime.class)) {
            script.append("'").append(((LocalDateTime) o)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("', ");
            return;
        }
        if(o.getClass().equals(String.class)){
            script.append("'").append(o).append("', ");
            return;
        }
        script.append(o).append(", ");
    }

    public static String primaryKey(Field f){
        return f.getAnnotation(PrimaryKey.class).autoInc()?"":f.getName().toLowerCase();
    }
}
