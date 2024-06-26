package com.example.coursework_ipz.dao;



import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbManagerMySQL implements DbManager {

    private static DbManagerMySQL instance;

    private final BasicDataSource basicDataSource;

    private DbManagerMySQL() {
        basicDataSource = new BasicDataSource();
        ResourceBundle resource = ResourceBundle.getBundle("database");
        basicDataSource.setDriverClassName(resource.getString("db.driverClassName"));
        basicDataSource.setUrl(resource.getString("db.url"));
        basicDataSource.setMaxIdle(Integer.parseInt(resource.getString("db.maxIdle")));
        basicDataSource.setMaxWait(Long.parseLong(resource.getString("db.maxWaitMillis")));
        basicDataSource.setMaxActive(Integer.parseInt(resource.getString("db.maxTotal")));
        basicDataSource.setUsername(resource.getString("db.username"));
        basicDataSource.setPassword(resource.getString("db.password"));
        basicDataSource.setConnectionProperties("connection security level=10;");
    }

    public static synchronized DbManagerMySQL getInstance() {
        if (instance == null) {
            instance = new DbManagerMySQL();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }

}