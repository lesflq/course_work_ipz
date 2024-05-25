module com.example.trytosmth {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires junit;
    requires mockito.all;
    requires commons.dbcp;

    requires org.testng;

    opens com.example.coursework_ipz to javafx.fxml;
    exports com.example.coursework_ipz;
    exports com.example.coursework_ipz.dao;
    opens com.example.coursework_ipz.dao to javafx.fxml, mockito.all;
    exports com.example.coursework_ipz.model;
    opens com.example.coursework_ipz.model to javafx.fxml;
    exports com.example.coursework_ipz.service;
    opens com.example.coursework_ipz.service to javafx.fxml;
}