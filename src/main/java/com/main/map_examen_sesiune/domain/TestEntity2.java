package com.main.map_examen_sesiune.domain;


import java.time.LocalDateTime;

public class TestEntity2 extends TestBaseClass{
    LocalDateTime column1;
    public TestEntity2(int id, LocalDateTime l){
        super(id);
        column1 = l;
    }
}
