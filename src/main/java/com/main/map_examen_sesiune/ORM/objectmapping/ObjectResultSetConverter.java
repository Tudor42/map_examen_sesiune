package com.main.map_examen_sesiune.ORM.objectmapping;

import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.ClassMethodsException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectResultSetConverter {
    public static Object convert(ResultSet resultSet, Class<?> cl) throws ClassMethodsException, SQLException, IllegalAccessException {
        Object o;
        try {
            o = cl.getConstructor().newInstance();
        }catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException n){
            throw new ClassMethodsException("The class " + cl.getSimpleName() + " has not default constructor");
        }
        for(Field f: FieldsParser.getAllFields(cl)){
            f.setAccessible(true);
            f.set(o, resultSet.getObject(f.getName().toLowerCase()));
            f.setAccessible(false);
        }
        return o;
    }
}
