package com.main.map_examen_sesiune.ORM.objectmapping;

import com.main.map_examen_sesiune.ORM.annotations.columntype.EnumType;
import com.main.map_examen_sesiune.ORM.annotations.columntype.Enumerated;
import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.ClassMethodsException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Arrays;

public class ObjectResultSetConverter {
    public static <T> T convert(ResultSet resultSet, Class<T> cl) throws ClassMethodsException, SQLException, IllegalAccessException {
        T o;
        try {
            o = cl.getConstructor().newInstance();
        }catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException n){
            throw new ClassMethodsException("The class " + cl.getSimpleName() + " has not default constructor");
        }
        for(Field f: FieldsParser.getAllFields(cl)){
            f.setAccessible(true);
            if(f.getAnnotation(Enumerated.class) != null){
                if(f.getAnnotation(Enumerated.class).type().equals(EnumType.STRING)) {
                    String tmp_str = resultSet.getObject(f.getName().toLowerCase()).toString();
                    Object tmp = Arrays.stream(f.getType().getEnumConstants()).
                            filter(x-> tmp_str.equals(x.toString())).toList().get(0);
                    f.set(o, tmp);
                }else{
                    Integer tmp = (Integer) resultSet.getObject(f.getName().toLowerCase());
                    f.set(o, f.getType().getEnumConstants()[tmp]);
                }
            }else {
                Object objf = resultSet.getObject(f.getName().toLowerCase());
                if(objf == null) continue;
                if(objf.getClass().equals(java.sql.Date.class)){
                    f.set(o, ((Date) objf).toLocalDate());
                    continue;
                }
                if(objf.getClass().equals(java.sql.Time.class)){
                    f.set(o, ((Time) objf).toLocalTime());
                    continue;
                }
                if(objf.getClass().equals(java.sql.Timestamp.class)){
                    f.set(o, ((Timestamp)objf).toLocalDateTime());
                    continue;
                }
                f.set(o, objf);
            }
            f.setAccessible(false);
        }
        return o;
    }
}
