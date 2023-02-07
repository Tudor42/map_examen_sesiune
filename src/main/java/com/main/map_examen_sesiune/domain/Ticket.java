package com.main.map_examen_sesiune.domain;


import com.main.map_examen_sesiune.ORM.annotations.TableNameAnnotation;
import com.main.map_examen_sesiune.ORM.annotations.columntype.FkRules;
import com.main.map_examen_sesiune.ORM.annotations.columntype.ForeignKey;
import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;

import java.time.LocalDateTime;

@TableNameAnnotation(tableName = "Tickets")
public class Ticket {
    @PrimaryKey @ForeignKey(entity = Client.class, referencedColumn = "username", ruleDelete = FkRules.CASCADE)
    String username;

    @PrimaryKey @ForeignKey(entity = Flight.class, referencedColumn = "flightId", ruleDelete = FkRules.SET_NULL)
    Long flightId;

    LocalDateTime purchaseTime;

}
