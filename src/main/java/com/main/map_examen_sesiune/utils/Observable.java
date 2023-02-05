package com.main.map_examen_sesiune.utils;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> observers=new ArrayList<>();

    public void addObserver(Observer e) {
        observers.add(e);
    }

    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    public void notifyObservers() {
        observers.forEach(x-> {
            try {
                x.update();
            } catch (SQLException | OrmException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}