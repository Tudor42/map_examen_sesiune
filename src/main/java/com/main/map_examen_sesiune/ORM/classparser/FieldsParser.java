package com.main.map_examen_sesiune.ORM.classparser;

import com.main.map_examen_sesiune.ORM.exceptions.NullObjectException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class FieldsParser {
    public static ArrayList<Field> getAllFields(Class<?> cl){
        if(checkClass(cl)){
            ArrayList<Field> result = new ArrayList<>(Arrays.asList(cl.getDeclaredFields()));
            result.addAll(getAllFields(cl.getSuperclass()));
            return result;
        }
        return new ArrayList<>();
    }
    public static ArrayList<Object> getAllFieldsValues(Object obj, ArrayList<Field> fields)
            throws NullObjectException, IllegalAccessException {
        ArrayList<Object> res = new ArrayList<>();
        if(obj == null){
            throw new NullObjectException("getAllFieldsValues: object passed is null");
        }
        for(Field f: fields){
            f.setAccessible(true);
            res.add(f.get(obj));
            f.setAccessible(false);
        }
        return res;
    }
    private static boolean checkClass(Class<?> cl){
        return !(cl == null || cl.getSimpleName().equals("Object"));
    }
}
