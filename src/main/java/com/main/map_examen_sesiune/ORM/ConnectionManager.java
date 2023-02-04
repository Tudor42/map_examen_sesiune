package com.main.map_examen_sesiune.ORM;

import com.main.map_examen_sesiune.ORM.exceptions.OrmException;
import com.main.map_examen_sesiune.ORM.objectmapping.ObjectResultSetConverter;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionManager {
    private String url;
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

    public ArrayList<Object> executeQuerySql(String query, Class<?> cl) throws OrmException,
            SQLException, IllegalAccessException {
        ArrayList<Object> objects = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet res = statement.executeQuery()){
            while(res.next()){
                objects.add(ObjectResultSetConverter.convert(res, cl));
            }
        }
        return objects;
    }

    public void executeUpdateSql(String sql) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.executeUpdate();
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
}
