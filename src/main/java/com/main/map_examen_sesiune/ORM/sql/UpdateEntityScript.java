package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateEntityScript {
    public static HashMap<Integer, Object> getUpdateScript(Object obj, ArrayList<Field> fields)
            throws IllegalAccessException, OrmException {
        // Update row with obj.pk to all the fields' values of obj that are included in fields param
        HashMap<Integer, Object> result = new HashMap<>();
        int nextPlaceHolderIndex = 1;
        StringBuilder script = new StringBuilder("UPDATE " + obj.getClass().getSimpleName().toLowerCase());
        script.append(" SET ");
        for(Field f: fields){
            script.append(f.getName().toLowerCase()).append("=?, ");
        }
        script.setLength(script.length()-2);
        script.append(" WHERE ");
        ArrayList<Field> pks = FieldsParser.getPkFields(obj.getClass());
        for(Field f: pks){
            script.append(f.getName().toLowerCase()).append("=? AND ");
        }
        for(Object o: FieldsParser.getAllFieldsValues(obj, fields)){
            result.put(nextPlaceHolderIndex, o);
            nextPlaceHolderIndex+=1;
        }
        for(Object o: FieldsParser.getAllFieldsValues(obj, pks)){
            result.put(nextPlaceHolderIndex, o);
            nextPlaceHolderIndex+=1;
        }
        script.setLength(script.length()-4);
        result.put(0, script.toString());
        return result;
    }
}
