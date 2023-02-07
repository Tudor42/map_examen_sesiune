package com.main.map_examen_sesiune;

import com.main.map_examen_sesiune.ORM.ConnectionManager;
import com.main.map_examen_sesiune.ORM.ORM;
import com.main.map_examen_sesiune.domain.Client;
import com.main.map_examen_sesiune.domain.Flight;
import com.main.map_examen_sesiune.domain.Ticket;

import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args){
        try{
            ConnectionManager connectionManager = new ConnectionManager("examen_map");
            System.out.println(connectionManager.url);
            ORM orm = new ORM(true, connectionManager, Client.class, Flight.class, Ticket.class);
            orm.insertEntity(new Client("username1", "Tudor"));
            orm.insertEntity(new Client("username2", "Vasile"));
            orm.insertEntity(new Client("username3", "Ion"));
            orm.insertEntity(new Client("username4", "Maria"));

            orm.insertEntity(new Flight("Location1", "Location2", LocalDateTime.of(2023, 10, 1, 10, 10), LocalDateTime.of(2002, 10, 2, 10, 10), 20));
            orm.insertEntity(new Flight("Location2", "Location1", LocalDateTime.of(2023, 10, 1, 13, 10), LocalDateTime.of(2002, 10, 2, 14, 10), 20));
            orm.insertEntity(new Flight("Location3", "Location1", LocalDateTime.of(2023, 10, 1, 15, 10), LocalDateTime.of(2002, 10, 2, 16, 10), 20));
            orm.insertEntity(new Flight("Location2", "Location3", LocalDateTime.of(2023, 10, 8, 18, 10), LocalDateTime.of(2002, 10, 9, 14, 10), 20));
            orm.insertEntity(new Flight("Location2", "Location4", LocalDateTime.of(2023, 10, 13, 13, 10), LocalDateTime.of(2002, 10, 18, 14, 10), 20));

            orm.getEntitiesWithProps(Flight.class, new Flight("Location1", "Location2", LocalDateTime.of(2023, 10, 1, 10, 10), LocalDateTime.of(2002, 10, 2, 10, 10), 20), "fromLocation").
                    forEach(x->System.out.println(x.toString()));

        }catch (Exception e){
            System.out.println("Error in database creation");
            System.out.println(e.getMessage());
        }
        //GUI.main(args);
    }
}
