package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.TableNameAnnotation;
import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;

import java.time.LocalDateTime;

@TableNameAnnotation(tableName = "Flights")
public class Flight {
    @PrimaryKey(autoInc = true)
    Long flightId;

    String fromLocation;
    String toLocation;

    LocalDateTime departureTime;

    LocalDateTime landingTime;

    Integer seats;

    public Flight(){

    }

    public Flight(String from, String to, LocalDateTime departureTime, LocalDateTime landingTime, Integer seats){
        this.fromLocation = from;
        this.toLocation = to;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return fromLocation + " " + toLocation + departureTime;
    }
}