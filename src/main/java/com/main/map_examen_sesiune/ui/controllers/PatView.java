package com.main.map_examen_sesiune.ui.controllers;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.domain.Pacient;
import com.main.map_examen_sesiune.domain.Pat;
import com.main.map_examen_sesiune.domain.TipPat;
import com.main.map_examen_sesiune.service.Service;
import com.main.map_examen_sesiune.ui.GUI;
import com.main.map_examen_sesiune.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PatView implements Observer {
    Service serv;
    public ObservableList<Pat> patList = FXCollections.observableArrayList();

    @FXML
    TextField tip1pat;

    @FXML
    TextField tip2pat;

    @FXML
    TextField tip3pat;

    @FXML
    TextField paturilibere;

    public void setServ(Service s) throws OrmException, SQLException, IllegalAccessException {
        this.serv = s;
        s.addObserver(this);
        init();
    }

    @FXML
    void initialize(){

    }

    public void init() throws OrmException, SQLException, IllegalAccessException {
        int i1 = serv.getNrOfPatType(TipPat.TIC), i2 = serv.getNrOfPatType(TipPat.TIM),
                i3 =serv.getNrOfPatType(TipPat.TIIP);
        tip1pat.setText("TIC " + i1 + " paturi libere");
        tip2pat.setText("TIM " + i2 + " paturi libere");
        tip3pat.setText("TIIP " + i3 + " paturi libere");
        paturilibere.setText("Paturi ocupat: " + (serv.getAllPat().size()-i1-i2-i3));
    }

    @Override
    public void update() throws SQLException, OrmException, IllegalAccessException {
        init();
    }
}
