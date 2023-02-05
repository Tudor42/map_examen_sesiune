package com.main.map_examen_sesiune.ui.controllers;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.utils.Observer;

import java.sql.SQLException;

public class DummyController implements Observer{
    @Override
    public void update() throws SQLException, OrmException, IllegalAccessException {

    }
}
