package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.annotations.TableNameAnnotation;
import com.main.map_examen_sesiune.ORM.annotations.columntype.NotNull;
import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;
import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class InsertEntityScript {
    public static HashMap<Integer, Object> getScript(Object obj) throws OrmException, IllegalAccessException {
        HashMap<Integer, Object> result = new HashMap<>();
        int nextPlaceHolderIndex = 1;
        StringBuilder script = new StringBuilder("INSERT INTO " +
                obj.getClass().getAnnotation(TableNameAnnotation.class).tableName().toLowerCase() + " (");
        ArrayList<Field> fieldsToGet = new ArrayList<>();
        for(Field f: FieldsParser.getAllFields(obj.getClass())){
            if(f.getAnnotation(PrimaryKey.class) != null){
                if(!f.getAnnotation(PrimaryKey.class).autoInc()) {
                    script.append(f.getName().toLowerCase()).append(", ");
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
            script.append("?, ");
            result.put(nextPlaceHolderIndex, o);
            nextPlaceHolderIndex+=1;
        }
        script.setLength(script.length()-2);
        script.append(")");
        result.put(0, script.toString());
        return result;
    }
}
