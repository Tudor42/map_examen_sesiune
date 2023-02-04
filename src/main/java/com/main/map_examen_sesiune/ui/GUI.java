package com.main.map_examen_sesiune.ui;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.domain.TestEntity1;
import com.main.map_examen_sesiune.domain.TestEntity2;
import com.main.map_examen_sesiune.domain.TestEntity3;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class GUI extends Application {
    public Stage stage;

    enum ex{
        RED,
        BLACK,
        WHITE
    }
    @Override
    public void start(Stage stage){
        ex e=ex.BLACK;
        System.out.println(e.ordinal());
        try{
            ConnectionManager cn = new ConnectionManager("testdatabase",
                    "postgres", "postgres", "5432");
            ORM orm = new ORM(true,cn, TestEntity1.class, TestEntity2.class, TestEntity3.class);
            orm.insertEntity(new TestEntity2(-1, LocalDateTime.now()));
            orm.insertEntity(new TestEntity3(-1, 1, false));
            orm.insertEntity(new TestEntity3(-1, 1, true));
            orm.insertEntity(new TestEntity3(-1, 1, true));
            for(Object obj: orm.getEntitiesWithProps(new TestEntity3())){
                TestEntity3 t = (TestEntity3) obj;
                System.out.println(""+ t.getId()+ " " + t.getFkTestEntity1());
            }

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        // TODO
        /*
        Test GetEntityScript
         */

    }

    public static void main(String [] args){
        launch(args);
    }
}