package com.main.map_examen_sesiune.service;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.domain.Pacient;
import com.main.map_examen_sesiune.domain.Pat;
import com.main.map_examen_sesiune.domain.TipPat;
import com.main.map_examen_sesiune.repository.RepoPacient;
import com.main.map_examen_sesiune.repository.RepoPat;
import com.main.map_examen_sesiune.utils.Observable;
import com.main.map_examen_sesiune.utils.Observer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Service extends Observable {
    public RepoPat repoPat = new RepoPat();
    public RepoPacient repoPacient = new RepoPacient();
    public Service() throws SQLException, OrmException {

    }
    public ArrayList<Pat> getAllPat() throws OrmException, SQLException, IllegalAccessException {
        return repoPat.getAll();
    }

    public int getNrOfPatType(TipPat tip) throws OrmException, SQLException, IllegalAccessException {
        return (int) repoPat.getAll().stream().filter(x->x.getTip().equals(tip) && x.getCNP()==null).count();
    }

    public ArrayList<Pacient> getAllPacient() throws OrmException, SQLException, IllegalAccessException {
        return repoPacient.getAll();
    }

    public ArrayList<Pacient> getAllPacientsWithNoBed() throws OrmException, SQLException, IllegalAccessException {
        ArrayList<String> tmp= getAllPat().stream().map(Pat::getCNP).collect(Collectors.toCollection(ArrayList::new));
        return repoPacient.getAll().stream().filter(x->!tmp.contains(x.getCNP())).collect(Collectors.toCollection(ArrayList::new));
    }


    public void addPacient(int idPat, String CNP) throws OrmException, SQLException, IllegalAccessException {
        repoPat.updateCNP(idPat, CNP);
        notifyObservers();
    }
}
