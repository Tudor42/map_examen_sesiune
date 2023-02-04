package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;

public class TestBaseClass {
    @PrimaryKey(autoInc = true)
    private int id;

    public TestBaseClass(int id) {
        this.id = id;
    }

    public TestBaseClass(){
        this.id = 0;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
