package com.main.map_examen_sesiune.ORM.exceptions;

public class ForeignKeyException extends OrmException{
    public ForeignKeyException(){super();}
    public ForeignKeyException(String msg){super(msg);}
}
