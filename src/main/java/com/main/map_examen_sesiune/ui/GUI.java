package com.main.map_examen_sesiune.ui;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.domain.TestEntity1;
import com.main.map_examen_sesiune.domain.TestEntity2;
import com.main.map_examen_sesiune.domain.TestEntity3;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {
    public Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        ConnectionManager cn = new ConnectionManager("testdatabase",
                "postgres", "postgres", "5432");
        try{ORM orm = new ORM(cn, TestEntity1.class, TestEntity2.class, TestEntity3.class);}
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String [] args){
        launch(args);
    }
}