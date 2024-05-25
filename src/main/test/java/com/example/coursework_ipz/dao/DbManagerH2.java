package com.example.coursework_ipz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManagerH2 implements DbManager{
    private static DbManagerH2 instance;

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
    }
    public static synchronized DbManagerH2 getInstance() {
        if (instance == null) {
            instance = new DbManagerH2();
        }
        return instance;
    }
}
