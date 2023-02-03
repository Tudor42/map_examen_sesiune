package com.main.map_examen_sesiune.ORM.exceptions;

public class OrmException extends Exception{
    public OrmException(){
        super();
    }
    public OrmException(String msg)
    {
        super("ORM: "+msg);
    }
}
