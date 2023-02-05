package com.main.map_examen_sesiune.repository;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

import java.sql.SQLException;

public class RepoExample {
    private final ORM orm;

    public RepoExample() throws SQLException, OrmException {
        this.orm = new ORM(new ConnectionManager("testdatabase"));
    }
}
