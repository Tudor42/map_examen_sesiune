package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.TableNameAnnotation;
import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;

@TableNameAnnotation(tableName = "Clienti")
public class Client{
    @PrimaryKey
    String username;
    String name;

    public Client(String username, String name){
        this.username = username;
        this.name = name;
    }
}
