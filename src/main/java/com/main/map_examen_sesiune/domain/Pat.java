package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.columntype.Enumerated;
import com.main.map_examen_sesiune.ORM.annotations.columntype.ForeignKey;
import com.main.map_examen_sesiune.ORM.annotations.columntype.NotNull;
import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;

public class Pat {
    @PrimaryKey(autoInc = true)
    int id;

    @Enumerated
    TipPat tip;

    @NotNull
    boolean ventilatie;

    @ForeignKey(entity = Pacient.class, referencedColumn = "CNP")
    String CNP;

    public Pat(){
    }

    public Pat(int id, String CNP){
        this.id = id;
        this.CNP = CNP;
    }
    public Pat(TipPat tip, boolean vent, String CNP){
        this.CNP = CNP;
        this.ventilatie = vent;
        this.tip = tip;
    }

    public String getCNP() {
        return CNP;
    }

    public TipPat getTip() {
        return tip;
    }

    public int getId() {
        return id;
    }
}
