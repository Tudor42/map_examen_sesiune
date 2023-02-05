package com.main.map_examen_sesiune.utils;

import java.sql.SQLException;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
public interface Observer {
    void update() throws SQLException, OrmException, IllegalAccessException;
}