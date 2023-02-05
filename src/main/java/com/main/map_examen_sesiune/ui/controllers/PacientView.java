package com.main.map_examen_sesiune.ui.controllers;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.domain.Pacient;
import com.main.map_examen_sesiune.domain.Pat;
import com.main.map_examen_sesiune.domain.TipPat;
import com.main.map_examen_sesiune.service.Service;
import com.main.map_examen_sesiune.utils.MessageAlert;
import com.main.map_examen_sesiune.utils.Observable;
import com.main.map_examen_sesiune.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PacientView implements Observer {
    Service serv;

    private final ObservableList<Pacient> pacientList = FXCollections.observableArrayList();
    private final ObservableList<Pat> paturiList = FXCollections.observableArrayList();
    public void setServ(Service s) throws OrmException, SQLException, IllegalAccessException {
        this.serv = s;
        s.addObserver(this);
        init();
    }
    @FXML
    TableView<Pacient> table;
    @FXML
    TableColumn<Pacient, String> CNP;
    @FXML
    TableColumn<Pacient, String> diagnostic;
    @FXML
    TableColumn<Pacient, Integer> gravitate;

    @FXML
    TableView<Pat> paturi;

    @FXML
    TableColumn<Pat, Integer> id;

    @FXML
    TableColumn<Pat, TipPat> tip;

    @FXML
    void initialize(){
        CNP.setCellValueFactory(new PropertyValueFactory<>("CNP"));
        diagnostic.setCellValueFactory(new PropertyValueFactory<>("diagnostic"));
        gravitate.setCellValueFactory(new PropertyValueFactory<>("gravitate"));
        table.setItems(pacientList);
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        paturi.setItems(paturiList);
    }

    public void init() throws OrmException, SQLException, IllegalAccessException {
        ArrayList<Pacient> p = serv.getAllPacientsWithNoBed();
        p.sort((x,y)->y.getGravitate()-x.getGravitate());
        pacientList.clear();
        pacientList.addAll(p);
        paturiList.clear();
        paturiList.addAll(serv.getAllPat().stream().filter(x->x.getCNP()==null).collect(Collectors.toCollection(ArrayList::new)));
    }

    public void addPacient(){
        try{
            Pat p = paturi.getSelectionModel().getSelectedItem();
            Pacient pacient = table.getSelectionModel().getSelectedItem();
            serv.addPacient(p.getId(), pacient.getCNP());
        }catch (Exception e){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare", "In procesul de adaugare a avut loc o eroare");
        }
    }

    @Override
    public void update() throws SQLException, OrmException, IllegalAccessException {
        init();
    }
}
