package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;
import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetEntityScript {
    public static String getEntityScript(Object o) throws OrmException, IllegalAccessException {
        // Get entity by primary key
        StringBuilder res = new StringBuilder("SELECT * FROM ");
        res.append(o.getClass().getSimpleName().toLowerCase()).append(" WHERE ");
        ArrayList<Field> pkFields = new ArrayList<>(FieldsParser.
                getAllFields(o.getClass()).stream().filter(x->x.getAnnotation(PrimaryKey.class)!=null).toList());
        if(pkFields.isEmpty()){
            throw new OrmException("No primary key field was specified for " + o.getClass().getSimpleName());
        }
        res.append(pkFields.get(0).getName()).append("=");
        pkFields.get(0).setAccessible(true);
        if(pkFields.get(0).getType().equals(String.class)) {
            res.append("'").append(pkFields.get(0).get(o)).append("'");
        }else{
            res.append(pkFields.get(0).get(o));
        }
        pkFields.get(0).setAccessible(false);
        res.append(" LIMIT 1");
        return res.toString();
    }
    public static String getEntitiesScript(Object props, ArrayList<Field> fields) throws IllegalAccessException {
        // Get entities with props
        StringBuilder script = new StringBuilder("SELECT * FROM ");
        script.append(props.getClass().getSimpleName().toLowerCase()).append(" WHERE ");
        for(Field f: fields){
            script.append(f.getName().toLowerCase()).append("=");
            f.setAccessible(true);
            Object tmp = f.get(props);
            if(tmp.getClass().equals(LocalDateTime.class)) {
                script.append("'").append(((LocalDateTime) tmp)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("' AND ");
            }else if(tmp.getClass().equals(String.class)){
                script.append("'").append(tmp).append("' AND ");
            }else {
                script.append(tmp).append(" AND ");
            }
            f.setAccessible(false);
        }
        script.setLength(script.length()-5);
        return script.toString();
    }
}
