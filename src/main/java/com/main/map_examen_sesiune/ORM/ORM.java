package com.main.map_examen_sesiune.ORM;

import com.main.map_examen_sesiune.ORM.annotations.columntype.ForeignKey;
import com.main.map_examen_sesiune.ORM.classparser.FieldsParser;
import com.main.map_examen_sesiune.ORM.exceptions.*;
import com.main.map_examen_sesiune.ORM.sql.CreateTableWriter;
import com.main.map_examen_sesiune.ORM.sql.GetEntityScript;
import com.main.map_examen_sesiune.ORM.sql.InsertEntityScript;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ORM {
    private final ConnectionManager connManager;
    private List<Class<?>> classList;

    public ORM(ConnectionManager conn, Class<?>... classes) throws TypeConversionFailedException,
            SQLException, ForeignKeyException {
        this.connManager = conn;
        classList = Arrays.stream(classes).collect(Collectors.toList());
        classList = partiallyOrderClasses();
        createTables();
    }
    public ORM(boolean dropTablesBeforeCreating, ConnectionManager conn, Class<?>... classes) throws TypeConversionFailedException,
            SQLException, ForeignKeyException {
        this.connManager = conn;
        classList = Arrays.stream(classes).collect(Collectors.toList());
        classList = partiallyOrderClasses();
        if(dropTablesBeforeCreating){
            dropTables();
        }
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
        ArrayList<Class<?>> s = new ArrayList<>(classList.stream().filter( // set of all classes with no reference to them
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

    private void dropTables() throws SQLException {
        StringBuilder script = new StringBuilder();
        for(int i = classList.size()-1; i>=0; i--){
            if(connManager.checkTableExists(classList.get(i).getSimpleName().toLowerCase())){
                script.append("DROP TABLE ").append(classList.get(i).getSimpleName()).append(";\n");
            }
        }
        connManager.executeUpdateSql(script.toString());
    }

    private void createTables() throws SQLException, TypeConversionFailedException {
        StringBuilder script = new StringBuilder();
        for(Class<?> cl: classList){
            if(!connManager.checkTableExists(cl.getSimpleName().toLowerCase())){
                script.append(CreateTableWriter.getScript(cl)).append(";\n");
            }
        }
        connManager.executeUpdateSql(script.toString());
    }

    public void insertEntity(Object obj) throws SQLException, OrmException, IllegalAccessException {
        if(!connManager.checkTableExists(obj.getClass().getSimpleName().toLowerCase())){
            throw new ClassToTableMappingException("MappingError: Entity provided to insert" +
                    " entity is not a part of database");
        }
        connManager.executeUpdateSql(InsertEntityScript.getScript(obj));
    }

    public Object getEntityByPk(Object obj) throws Exception {
        if(!connManager.checkTableExists(obj.getClass().getSimpleName().toLowerCase())){
            throw new ClassToTableMappingException("MappingError: Entity provided to insert" +
                    " entity is not a part of database");
        }
        ArrayList<Object> objs = connManager.executeQuerySql(GetEntityScript.getEntityScript(obj), obj.getClass());
        if(objs.isEmpty()) return null;
        return objs.get(0);
    }

    public ArrayList<Object> getEntitiesWithProps(Object props, String... fields) throws OrmException,
            SQLException, IllegalAccessException {
        if(!connManager.checkTableExists(props.getClass().getSimpleName().toLowerCase())){
            throw new ClassToTableMappingException("MappingError: Entity provided to insert" +
                    " entity is not a part of database");
        }
        ArrayList<Field> f = new ArrayList<>();
        {
            ArrayList<Field> tmp = FieldsParser.getAllFields(props.getClass());
            for(String fs:Arrays.stream(fields).collect(Collectors.toCollection(ArrayList::new))){
                try{
                    f.add(tmp.stream().
                            filter(x-> x.getName().equalsIgnoreCase(fs)).toList().get(0));
                }catch (IndexOutOfBoundsException e){
                    throw new ClassFieldException("field " + fs +
                            " provided to getEntitiesWithProps doesnt exist in class " +
                            props.getClass().getSimpleName());
                }
            }
        }
        return connManager.executeQuerySql(GetEntityScript.getEntitiesScript(props, f), props.getClass());
    }

}
