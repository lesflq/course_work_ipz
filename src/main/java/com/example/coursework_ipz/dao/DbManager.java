package com.example.coursework_ipz.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbManager {
    Connection getConnection() throws SQLException;
}
