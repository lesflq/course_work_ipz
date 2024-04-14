module com.example.trytosmth {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires commons.dbcp;

    opens com.example.trytosmth to javafx.fxml;
    exports com.example.trytosmth;
    exports com.example.trytosmth.dao;
    opens com.example.trytosmth.dao to javafx.fxml;
    exports com.example.trytosmth.model;
    opens com.example.trytosmth.model to javafx.fxml;
    exports com.example.trytosmth.service;
    opens com.example.trytosmth.service to javafx.fxml;
}