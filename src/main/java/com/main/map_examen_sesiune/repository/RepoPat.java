package com.main.map_examen_sesiune.repository;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.domain.Pat;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepoPat {
    private final ORM orm;
    public RepoPat() throws SQLException, OrmException {
        this.orm = new ORM(new ConnectionManager("testdatabase"));
    }

   public ArrayList<Pat> getAll() throws OrmException, SQLException, IllegalAccessException {
        ArrayList<Pat> res = new ArrayList<>();
        for(Object o: this.orm.getEntitiesWithProps(new Pat())){
            res.add((Pat) o);
        }
        return res;
   }

   public void updateCNP(int id, String CNP) throws OrmException, SQLException, IllegalAccessException {
        orm.updateEntity(new Pat(id, CNP), "CNP");
   }
}
