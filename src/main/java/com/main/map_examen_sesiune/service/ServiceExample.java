package com.main.map_examen_sesiune.service;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.repository.RepoExample;
import com.main.map_examen_sesiune.utils.Observable;

import java.sql.SQLException;

public class ServiceExample extends Observable {
    RepoExample repo = new RepoExample();

    public ServiceExample() throws SQLException, OrmException {
    }
}
