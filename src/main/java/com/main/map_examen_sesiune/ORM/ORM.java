package com.main.map_examen_sesiune.ORM;

import com.main.map_examen_sesiune.ORM.annotations.columntype.ForeignKey;
import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.ForeignKeyException;
import com.main.map_examen_sesiune.ORM.exceptions.TypeConversionFailedException;
import com.main.map_examen_sesiune.ORM.sql.CreateTableWriter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ORM {
    private final ConnectionManager connManager;
    private final List<Class<?>> classList;


    public ORM(ConnectionManager conn, Class<?>... classes) throws TypeConversionFailedException,
            SQLException, ForeignKeyException {
        this.connManager = conn;
        classList = Arrays.stream(classes).collect(Collectors.toList());
        createTables();
    }

    private boolean fkFromClass1ToClass2(Class<?> class1, Class<?> class2){
        return FieldsParser.getAllFields(class1).stream().filter(x -> {
            ForeignKey a = x.getAnnotation(ForeignKey.class);
            return a != null && a.entity() == class2;
        }).toList().size() != 0;
    }

    private List<Class<?>> partiallyOrderClasses() throws ForeignKeyException {
        // Kahn's algorithm for partial sorting
        ArrayList<Class<?>> result = new ArrayList<>();
        HashMap<Class<?>, ArrayList<Class<?>>> adjList = new HashMap<>();
        for(Class<?> cl: this.classList){
            adjList.put(cl, new ArrayList<>());
            for(Class<?> cl1: this.classList){
                if(fkFromClass1ToClass2(cl1, cl)){ // the direction of edges is opposite to the direction of reference
                    adjList.get(cl).add(cl1);
                }
            }
        }
        ArrayList<Class<?>> s = new ArrayList<Class<?>>(classList.stream().filter( // set of all classes with no reference to them
                x -> classList.stream().filter(
                        y-> fkFromClass1ToClass2(x, y)
                ).toList().size() == 0
        ).toList());

        while(!s.isEmpty()){
            Class<?> cl = s.get(0);
            s.remove(cl);
            result.add(cl);
            ArrayList<Class<?>> tmp = new ArrayList<>(adjList.get(cl));
            for(Class<?> m : tmp){
                adjList.get(cl).remove(m);
                boolean incomingEdges = false;
                for(ArrayList<Class<?>> l: adjList.values()){
                    if(l.contains(m)){
                        incomingEdges = true;
                        break;
                    }
                }
                if(!incomingEdges){
                    s.add(m);
                }
            }
        }
        if(!adjList.values().stream().filter(x->!x.isEmpty()).toList().isEmpty()){
            throw new ForeignKeyException("Foreign key: fk cycle (e.g. A references B, B references C, C references A");
        }
        return result;
    }

    private void createTables() throws ForeignKeyException, SQLException, TypeConversionFailedException {
        // partially order the list of classes
        List<Class<?>> partialOrderedClasses = partiallyOrderClasses();
        StringBuilder script = new StringBuilder();
        for(Class<?> cl: partialOrderedClasses){
            if(!connManager.checkTableExists(cl.getName())){
                script.append(CreateTableWriter.getScript(cl)).append("\n");
            }
        }
        System.out.println(script);
    }

}
