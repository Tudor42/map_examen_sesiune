package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.columntype.ForeignKey;

public class TestEntity3 extends TestBaseClass{
    @ForeignKey(entity = TestEntity2.class)
    int fkTestEntity1;

    boolean column1;

    public TestEntity3(){
    }

    public TestEntity3(int id, int fkTestEntity1, boolean b){
        super(id);
        this.fkTestEntity1 = fkTestEntity1;
        column1 = b;
    }

    public int getFkTestEntity1() {
        return fkTestEntity1;
    }
    public boolean isColumn1() {
        return column1;
    }
}
