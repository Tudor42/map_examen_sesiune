package com.main.map_examen_sesiune.ORM.sql;

import com.main.map_examen_sesiune.ORM.annotations.columntype.*;
import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.ClassFieldException;
import com.main.map_examen_sesiune.ORM.exceptions.TypeConversionFailedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CreateTableWriter {
    public static String getScript(Class<?> cl) throws TypeConversionFailedException, ClassFieldException {
        StringBuilder res = new StringBuilder("CREATE TABLE " + cl.getSimpleName() + "(");
        ArrayList<Field> pks = FieldsParser.getPkFields(cl);
        if(pks.size() < 1 || pks.size() > 2){
            throw new ClassFieldException("For class "+cl.getSimpleName()+" the primary key is not " +
                    "specified or the number of pks is greater than 2");
        }
        if(pks.stream().filter(x->x.getAnnotation(PrimaryKey.class).autoInc()).toList().size() == 2){
            throw new ClassFieldException("For class "+cl.getSimpleName()+" were specified a primary key " +
                    "as a pair of columns " +
                    "autoInc should be false for both columns");
        }

        for(Field field: FieldsParser.getAllFields(cl)){
            res.append(fieldResolve(field));
            if(pks.contains(field)){
               if(pks.size() == 1){
                   res.append(" PRIMARY KEY ").
                           append(field.getAnnotation(PrimaryKey.class).autoInc() ? " GENERATED BY DEFAULT AS IDENTITY" : "");
               }
            }
            res.append(", ");
        }
        if(pks.size()==2){
            res.append(" PRIMARY KEY (").append(pks.get(0).getName()).
                    append(", ").append(pks.get(1).getName()).append(")  ");
        }
        return res.substring(0, res.length()-2) + ")";
    }

    private static String foreignKeyString(Field field){
        ForeignKey an = field.getAnnotation(ForeignKey.class);

        return "FOREIGN KEY (" + field.getName() + ") REFERENCES " + an.entity().getSimpleName() +
        "(" + an.referencedColumn() + ")"
                + (an.ruleDelete() == FkRules.CASCADE?
                " ON DELETE CASCADE ":
                an.ruleDelete() == FkRules.SET_NULL?
                        " ON DELETE SET NULL ":"")
                + (an.ruleUpdate() == FkRules.CASCADE?
                "ON UPDATE CASCADE ":
                an.ruleUpdate() == FkRules.SET_NULL?
                        "ON UPDATE SET NULL ":"");
    }

    private static String foreignKey(Field field){
        ForeignKey an = field.getAnnotation(ForeignKey.class);
        if(an == null){
            return "";
        }
        if(an.addFKAfter()){
            return "";
        }
        return ", " + foreignKeyString(field);
    }
    private static String fieldResolve(Field field) throws TypeConversionFailedException, ClassFieldException {
        String type = TypeConvertorJavaSQL.getSQLType(field.getType().getSimpleName());
        if(type==null && field.getAnnotation(Enumerated.class) == null){
            throw new ClassFieldException("Field " + field.getName() + " may be of type enum but is not specified. " +
                    "Please use @Enumerated annotation for this field");
        }
        return field.getName() + " " +  (type==null?TypeConvertorJavaSQL.
                getSQLType(field.getAnnotation(Enumerated.class).type().name().toLowerCase()):type)
                + (field.getAnnotation(NotNull.class) != null? " NOT NULL": "")
                + (field.getAnnotation(Unique.class) != null? " UNIQUE": "")
                + foreignKey(field);
    }

    public static String addFkAfterCreation(Class<?> cl){
        ArrayList<Field> fields = FieldsParser.getAllFields(cl).stream().
                filter(x->{
                    ForeignKey a = x.getAnnotation(ForeignKey.class);
                    return a!=null && a.addFKAfter();
                }).collect(Collectors.toCollection(ArrayList::new));

        StringBuilder res = new StringBuilder();
        for(Field f: fields) {
            res.append("ALTER TABLE ").append(cl.getSimpleName()).
                    append(" ADD CONSTRAINT ").append(f.getName().toLowerCase()).append("_fkConstraint ").
                    append(foreignKeyString(f)).append("; ");
        }
        return res.toString();
    }
}
