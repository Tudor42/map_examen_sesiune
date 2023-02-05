package com.main.map_examen_sesiune.ui;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;



public class GUI extends Application {

    public GUI() throws SQLException, OrmException {
    }

    @Override
    public void start(Stage stage) throws IOException, OrmException, SQLException, IllegalAccessException {

    }

    public static void main(String [] args){
        try{
            int i;
            //ConnectionManager connectionManager = new ConnectionManager("testdatabase");
            //ORM o = new ORM(connectionManager, Pacient.class, Pat.class);
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