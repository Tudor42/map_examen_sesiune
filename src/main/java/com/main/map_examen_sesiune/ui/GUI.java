package com.main.map_examen_sesiune.ui;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.domain.Pacient;
import com.main.map_examen_sesiune.domain.Pat;
import com.main.map_examen_sesiune.service.Service;
import com.main.map_examen_sesiune.ui.controllers.PacientView;
import com.main.map_examen_sesiune.ui.controllers.PatView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class GUI extends Application {

    public Service serv = new Service();

    public GUI() throws SQLException, OrmException {
    }

    @Override
    public void start(Stage stage) throws IOException, OrmException, SQLException, IllegalAccessException {
        Stage stage1 = new Stage();
        Stage stage2 = new Stage();

        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("pacient_view.fxml"));
        Parent root1 = loader1.load();
        ((PacientView)loader1.getController()).setServ(serv);
        ((PacientView)loader1.getController()).init();
        serv.addObserver(loader1.getController());

        stage1.setScene(new Scene(root1, 600, 600));
        stage1.show();

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("pat_view.fxml"));
        Parent root2 = loader2.load();
        ((PatView)loader2.getController()).setServ(serv);
        serv.addObserver(loader2.getController());
        stage2.setScene(new Scene(root2, 600, 600));
        stage2.show();
    }

    public static void main(String [] args){
        try{
            ConnectionManager connectionManager = new ConnectionManager("testdatabase");
            ORM o = new ORM(connectionManager, Pacient.class, Pat.class);
            /*for(int i = 0; i < 30; i++) {
                o.insertEntity(new Pacient(Integer.toString(i), i, i%2==0, "diagnostic" + i, (1+i)%5));
            }
            for(int i=0; i < 40; i++){
                if(i < 20)
                    o.insertEntity(new Pat(TipPat.values()[i%3], true, Integer.toString(i%30)));
                else
                    o.insertEntity(new Pat(TipPat.values()[i%3], true, null));
            }*/
        }catch (Exception e){
            System.out.println("Error in database creation");
            System.out.println(e.getMessage());
            return;
        }
        launch(args);
    }
}