package com.example.coursework_ipz.dao;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class DbManagerMySQLTest {

    private DbManagerMySQL dbManagerMySQL;

    @Test
    public void getInstanceTest() {
        dbManagerMySQL = DbManagerMySQL.getInstance();
        assertNotNull(dbManagerMySQL);
    }

}