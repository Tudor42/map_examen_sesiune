module com.main.map_examen_sesiune {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.main.map_examen_sesiune to javafx.fxml;
    exports com.main.map_examen_sesiune;
    exports com.main.map_examen_sesiune.ui;
}