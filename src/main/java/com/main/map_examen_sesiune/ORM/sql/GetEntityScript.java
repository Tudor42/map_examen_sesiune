package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class GetEntityScript {
    public static HashMap<Integer, Object> getEntityScript(Object o) throws IllegalAccessException {
        // Get entity by primary key
        return getEntitiesScript(o, FieldsParser.getPkFields(o.getClass()));
    }
    public static HashMap<Integer, Object> getEntitiesScript(Object props, ArrayList<Field> fields)
            throws IllegalAccessException {
        // Get entities with props
        HashMap<Integer, Object> result = new HashMap<>();
        int nextPlaceHolderIndex = 1;
        StringBuilder script = new StringBuilder("SELECT * FROM ");
        script.append(props.getClass().getSimpleName().toLowerCase()).append(" WHERE ");
        for(Field f: fields){
            script.append(f.getName().toLowerCase()).append("=? AND ");
            f.setAccessible(true);
            result.put(nextPlaceHolderIndex, f.get(props));
            nextPlaceHolderIndex += 1;
            f.setAccessible(false);
        }
        script.setLength(script.length()-4);
        result.put(0, script.toString());
        return result;
    }
}
