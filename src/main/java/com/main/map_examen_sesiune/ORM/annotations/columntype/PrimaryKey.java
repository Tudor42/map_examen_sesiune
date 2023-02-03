package com.main.map_examen_sesiune.ORM.annotations.columntype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {
    boolean autoInc() default false; // is primary key autoIncrementing
}
