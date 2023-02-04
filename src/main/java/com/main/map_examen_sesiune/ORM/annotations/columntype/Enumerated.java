package com.main.map_examen_sesiune.ORM.annotations.columntype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Enumerated {
    EnumType type() default EnumType.STRING;
}
