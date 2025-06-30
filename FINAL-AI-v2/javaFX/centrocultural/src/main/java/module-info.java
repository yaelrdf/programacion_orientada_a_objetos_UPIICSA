module com.centrocultural {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires org.mariadb.jdbc;
    requires javafx.graphics;
    
    opens com.centrocultural to javafx.fxml;
    opens com.centrocultural.controllers to javafx.fxml;
    opens com.centrocultural.models to javafx.base;
    
    exports com.centrocultural;
    exports com.centrocultural.controllers;
    exports com.centrocultural.models;
}