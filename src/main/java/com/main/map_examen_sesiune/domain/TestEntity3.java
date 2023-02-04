package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.columntype.ForeignKey;

public class TestEntity3 extends TestBaseClass{
    @ForeignKey(entity = TestEntity2.class)
    int fkTestEntity1;

    boolean column1;
}
