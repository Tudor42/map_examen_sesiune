module com.main.map_examen_sesiune {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.main.map_examen_sesiune to javafx.fxml;
    opens com.main.map_examen_sesiune.ui.controllers to javafx.fxml;
    exports com.main.map_examen_sesiune.ui.controllers;
    exports com.main.map_examen_sesiune.ui;
    exports com.main.map_examen_sesiune.domain;
    exports com.main.map_examen_sesiune.ORM;
    exports com.main.map_examen_sesiune.service;
    exports com.main.map_examen_sesiune.repository;
    exports com.main.map_examen_sesiune.ORM.exceptions;
}