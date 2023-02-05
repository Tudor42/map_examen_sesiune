package com.main.map_examen_sesiune.domain;

import com.main.map_examen_sesiune.ORM.annotations.columntype.NotNull;
import com.main.map_examen_sesiune.ORM.annotations.columntype.PrimaryKey;

public class Pacient {
    @PrimaryKey
    String CNP;

    @NotNull
    int varsta;

    @NotNull
    boolean prematur;

    @NotNull
    String diagnostic;

    @NotNull
    int gravitate;

    public Pacient(){

    }
    public Pacient(String CNP, int varsta, boolean prematur, String diagnostic, int gravitate){
        this.CNP = CNP;
        this.varsta = varsta;
        this.prematur = prematur;
        this.diagnostic = diagnostic;
        this.gravitate = gravitate;
    }

    public String getCNP() {
        return CNP;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public int getGravitate() {
        return gravitate;
    }
}
