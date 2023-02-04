package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.columntype.FkRules;
import com.main.map_examen_sesiune.ORM.annotations.columntype.ForeignKey;

import java.time.LocalDateTime;

public class TestEntity1 extends TestBaseClass{
    String column1;
    LocalDateTime COLUMN2;
    @ForeignKey(entity = TestEntity3.class, rule = FkRules.CASCADE)
    int fk1;
}
