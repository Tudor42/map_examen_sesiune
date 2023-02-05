package com.main.map_examen_sesiune.repository;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.domain.Pacient;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepoPacient {
    private final ORM orm;
    public RepoPacient() throws SQLException, OrmException {
        this.orm = new ORM(new ConnectionManager("testdatabase"));
    }

    public ArrayList<Pacient> getAll() throws OrmException, SQLException, IllegalAccessException {
        ArrayList<Pacient> res = new ArrayList<>();
        for(Object o: this.orm.getEntitiesWithProps(new Pacient())){
            res.add((Pacient) o);
        }
        return res;
    }
}