package com.main.map_examen_sesiune.ORM.classparser;

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

    private static boolean checkClass(Class<?> cl){
        return !(cl == null || cl.getSimpleName().equals("Object"));
    }
}
