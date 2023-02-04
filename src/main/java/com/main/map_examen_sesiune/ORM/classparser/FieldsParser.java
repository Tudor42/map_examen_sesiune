package com.main.map_examen_sesiune.ORM.classparser;

import com.main.map_examen_sesiune.ORM.annotations.columntype.EnumType;
import com.main.map_examen_sesiune.ORM.annotations.columntype.Enumerated;
import com.main.map_examen_sesiune.ORM.exceptions.NullObjectException;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

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
            throws OrmException, IllegalAccessException {
        ArrayList<Object> res = new ArrayList<>();
        if(obj == null){
            throw new NullObjectException("getAllFieldsValues: object passed is null");
        }
        for(Field f: fields){
            f.setAccessible(true);
            if(f.getAnnotation(Enumerated.class)!= null){
                if(f.getAnnotation(Enumerated.class).type() == EnumType.STRING){
                    res.add("'" + f.get(obj) + "'");
                }else if(f.getAnnotation(Enumerated.class).type() == EnumType.INT){
                    res.add(((EnumType)f.get(obj)).ordinal());
                }else{
                    throw new OrmException("Enumerated type is not implemented");
                }
            }else{
                res.add(f.get(obj));
            }
            f.setAccessible(false);
        }
        return res;
    }
    private static boolean checkClass(Class<?> cl){
        return !(cl == null || cl.getSimpleName().equals("Object"));
    }
}
