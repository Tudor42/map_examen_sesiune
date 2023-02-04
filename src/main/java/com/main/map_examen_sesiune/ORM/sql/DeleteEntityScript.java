package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class DeleteEntityScript {
    public static HashMap<Integer, Object> getScript(Object obj) throws OrmException, IllegalAccessException {
        HashMap<Integer, Object> result = new HashMap<>();
        int nextPlaceHolderIndex = 1;
        StringBuilder script = new StringBuilder("DELETE FROM " +
                obj.getClass().getSimpleName().toLowerCase() +" WHERE ");
        ArrayList<Field> pks = FieldsParser.getPkFields(obj.getClass());
        for(Field f: pks){
            script.append(f.getName().toLowerCase()).append("=? AND ");
        }
        script.setLength(script.length() - 4);
        for(Object o: FieldsParser.getAllFieldsValues(obj, pks)){
            result.put(nextPlaceHolderIndex, o);
            nextPlaceHolderIndex += 1;
        }

        result.put(0, script.toString());
        return result;
    }
}
