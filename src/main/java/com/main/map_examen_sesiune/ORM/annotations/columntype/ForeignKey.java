package com.main.map_examen_sesiune.ORM.annotations.columntype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {
    Class<?> entity();
    String referencedColumn() default "id";
    FkRules ruleDelete() default FkRules.NO_ACTION;
    FkRules ruleUpdate() default FkRules.NO_ACTION;
    boolean addFKAfter() default false;
}
