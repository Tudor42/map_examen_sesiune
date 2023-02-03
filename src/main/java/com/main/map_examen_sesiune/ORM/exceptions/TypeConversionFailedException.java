package com.main.map_examen_sesiune.ORM.exceptions;

public class TypeConversionFailedException extends OrmException {
    public TypeConversionFailedException(){
        super();
    }
    public TypeConversionFailedException(String msg)
    {
        super(msg);
    }
}