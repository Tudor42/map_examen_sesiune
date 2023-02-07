package com.main.map_examen_sesiune.ORM;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.ORM.objectmapping.ObjectResultSetConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
    public String url;
    private final String username;
    private final String password;
    private final String port;

    public ConnectionManager(String database) throws SQLException {
        this.username = "postgres";
        this.password = "postgres";
        this.port = "5432";
        if(checkDataBaseDoesNotExists(database)){
            createDatabase(database);
        }
        setDatabase(database);
        System.out.println("ConnectionManager instantiated without errors");
    }

    public ConnectionManager(String database, String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
        this.port = "5432";
        if(checkDataBaseDoesNotExists(database)){
            createDatabase(database);
        }
        setDatabase(database);
        System.out.println("ConnectionManager instantiated without errors");
    }

    public ConnectionManager(String database, String username, String password, String port) throws SQLException {
        this.username = username;
        this.password = password;
        this.port = port;
        if(checkDataBaseDoesNotExists(database)){
            createDatabase(database);
        }
        setDatabase(database);
        System.out.println("ConnectionManager instantiated without errors");
    }

    private void createDatabase(String database) throws SQLException {
        System.out.println("Creating database  " + database);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:"+this.port+"/postgres",
                this.username, this.password);
            Statement statement = connection.createStatement()
        ){
            statement.executeUpdate("CREATE DATABASE " + database);
            System.out.println("Database created successfully.");
        }
    }

    public void setDatabase(String database) {
        url = "jdbc:postgresql://localhost:"+port+"/"+database;
    }

    private boolean checkDataBaseDoesNotExists(String database) throws SQLException {
        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:"+this.port+"/postgres",
                this.username, this.password);
            Statement statement =  connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + database + "';")){
            return !res.next();
        }
    }

    public <T> ArrayList<T> executeQuerySql(HashMap<Integer, Object> query, Class<T> cl) throws OrmException,
            SQLException, IllegalAccessException {
        ArrayList<T> objects = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement((String) query.get(0))){
            query.remove(0);
            completePlaceHolders(query, statement);
            try(ResultSet res = statement.executeQuery()){
                while(res.next()){
                    try {
                        objects.add(ObjectResultSetConverter.convert(res, cl));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return objects;
    }

    public void executeUpdateSql(HashMap<Integer, Object> query) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement((String)query.get(0)))
        {
            query.remove(0);
            completePlaceHolders(query, statement);
            statement.executeUpdate();
        }
    }

    private static void completePlaceHolders(HashMap<Integer, Object> query, PreparedStatement statement) throws SQLException {
        for(Map.Entry<Integer, Object> f: query.entrySet()){
            if(f.getValue() == null){
                statement.setNull(f.getKey(), java.sql.Types.NULL);
                continue;
            }
            if(f.getValue().getClass() == String.class){
                statement.setString(f.getKey(), (String) f.getValue());
                continue;
            }
            if(f.getValue().getClass() == Long.class){
                statement.setLong(f.getKey(), (Long) f.getValue());
                continue;
            }
            if(f.getValue().getClass() == Short.class){
                statement.setShort(f.getKey(), (Short) f.getValue());
            }
            if(f.getValue().getClass() == Character.class){
                statement.setString(f.getKey(), f.getValue().toString());
                continue;
            }
            if(f.getValue().getClass() == LocalDate.class){
                statement.setDate(f.getKey(), Date.valueOf((LocalDate) f.getValue()));
                continue;

            }
            if(f.getValue().getClass() == Integer.class){
                statement.setInt(f.getKey(),(Integer) f.getValue());
                continue;

            }
            if(f.getValue().getClass() == Float.class){
                statement.setFloat(f.getKey(), (Float) f.getValue());
                continue;

            }
            if(f.getValue().getClass() == Boolean.class){
                statement.setBoolean(f.getKey(), (Boolean) f.getValue());
                continue;

            }
            if(f.getValue().getClass() == Double.class){
                statement.setDouble(f.getKey(), (Double) f.getValue());
                continue;

            }
            if(f.getValue().getClass() == LocalDateTime.class){
                statement.setTimestamp(f.getKey(), Timestamp.valueOf((LocalDateTime) f.getValue()));
                continue;

            }
            if(f.getValue().getClass() == LocalTime.class){
                statement.setTime(f.getKey(), Time.valueOf((LocalTime) f.getValue()));
            }
        }
    }

    public boolean checkTableExists(String tableName) throws SQLException{
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password))
        {
            DatabaseMetaData metaData = connection.getMetaData();
            try(ResultSet resultSet =
                        metaData.getTables(null, null, tableName, new String[]{"TABLE"})) {
                return resultSet.next();
            }
        }
    }

    public void dropAllTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT tablename FROM pg_tables WHERE schemaname = 'public'")) {
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                try (Statement dropStatement = connection.createStatement()) {
                    dropStatement.executeUpdate("DROP TABLE IF EXISTS " + tableName + " CASCADE");
                }
            }
        }
    }
}
