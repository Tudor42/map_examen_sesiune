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
    public void start(Stage stage){
        try{
            ConnectionManager cn = new ConnectionManager("testdatabase",
                    "postgres", "postgres", "5432");
            ORM orm = new ORM(cn, TestEntity1.class, TestEntity2.class, TestEntity3.class);
            orm.insertEntity(new TestEntity3(-1, 2, false));
            orm.insertEntity(new TestEntity3(-1, 1, false));
            orm.insertEntity(new TestEntity3(-1, 1, true));
            orm.insertEntity(new TestEntity3(-1, 1, true));

            for(Object t1:  orm.getEntitiesWithProps(
                    new TestEntity3(1, -1, false),
                    "column1")){
                TestEntity3 t = (TestEntity3) t1;
                System.out.println(t.getId());
                System.out.println(t.getFkTestEntity1());
                System.out.println(t.isColumn1());
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