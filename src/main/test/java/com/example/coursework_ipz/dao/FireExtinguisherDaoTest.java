package com.example.coursework_ipz.dao;

import com.example.coursework_ipz.model.FireExtinguisherData;
import com.example.coursework_ipz.dao.exception.DbException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FireExtinguisherDaoTest {

    private FireExtinguisherDao fireExtinguisherDao;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        DbManagerH2 instance = DbManagerH2.getInstance();
        connection = instance.getConnection();
        fireExtinguisherDao = new FireExtinguisherDao(instance);

        createTable();
    }

    @After
    public void tearDown() throws SQLException {
        dropTable();
        connection.close();
    }

    @Test
    public void testGetById() throws DbException, SQLException {
        // Arrange
        FireExtinguisherData expected = new FireExtinguisherData();
        expected.setId(1L);
        expected.setLocation("Location");
        expected.setExpirationDate(LocalDate.of(2025, 5, 24));

        insertData(expected);

        // Act
        FireExtinguisherData actual = fireExtinguisherDao.getById(1);

        // Assert
        assertNotNull("Expected non-null FireExtinguisherData", actual);
        assertEquals("Unexpected FireExtinguisherData", expected, actual);
    }

    @Test
    public void testGetAll() throws DbException, SQLException {
        // Arrange
        FireExtinguisherData data1 = new FireExtinguisherData();
        data1.setId(1L);
        data1.setLocation("Location1");
        data1.setExpirationDate(LocalDate.of(2025, 5, 24));

        FireExtinguisherData data2 = new FireExtinguisherData();
        data2.setId(2L);
        data2.setLocation("Location2");
        data2.setExpirationDate(LocalDate.of(2025, 5, 25));

        insertData(data1);
        insertData(data2);

        // Act
        List<FireExtinguisherData> dataList = fireExtinguisherDao.getAll();

        // Assert
        assertEquals("Unexpected number of FireExtinguisherData", 2, dataList.size());
        assertEquals("Unexpected FireExtinguisherData", data1, dataList.get(0));
        assertEquals("Unexpected FireExtinguisherData", data2, dataList.get(1));
    }

    @Test
    public void testDelete() throws DbException, SQLException {
        // Arrange
        FireExtinguisherData data1 = new FireExtinguisherData();
        data1.setId(1L);
        data1.setLocation("Location1");
        data1.setExpirationDate(LocalDate.of(2025, 5, 24));

        FireExtinguisherData data2 = new FireExtinguisherData();
        data2.setId(2L);
        data2.setLocation("Location2");
        data2.setExpirationDate(LocalDate.of(2025, 5, 25));

        insertData(data1);
        insertData(data2);

        // Act
        List<FireExtinguisherData> dataListBeforeDelete = fireExtinguisherDao.getAll();
        assertEquals("Unexpected number of FireExtinguisherData before delete", 2, dataListBeforeDelete.size());

        fireExtinguisherDao.delete(data1);
        List<FireExtinguisherData> dataListAfterDelete = fireExtinguisherDao.getAll();

        // Assert
        assertEquals("Unexpected number of FireExtinguisherData after delete", 1, dataListAfterDelete.size());
        assertEquals("Unexpected FireExtinguisherData", data2, dataListAfterDelete.get(0));
    }

    @Test
    public void testInsert() throws DbException, SQLException {
        // Arrange
        FireExtinguisherData data = new FireExtinguisherData();
        data.setId(1L);  // Це значення може бути ігноровано, якщо використовується AUTO_INCREMENT
        data.setLocation("NewLocation");
        data.setExpirationDate(LocalDate.of(2025, 5, 24));

        // Act
        fireExtinguisherDao.insert(data);
        List<FireExtinguisherData> dataList = fireExtinguisherDao.getAll();

        // Assert
        assertEquals("Unexpected number of FireExtinguisherData after insert", 1, dataList.size());
        FireExtinguisherData actual = dataList.get(0);
        assertNotNull("Expected non-null FireExtinguisherData", actual);
        assertEquals("Unexpected location", "NewLocation", actual.getLocation());
        assertEquals("Unexpected expiration date", LocalDate.of(2025, 5, 24), actual.getExpirationDate());
    }

    private void createTable() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE fire_extinguishers (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "location VARCHAR(255), " +
                "expirationDate DATE)");
    }

    private void dropTable() throws SQLException {
        connection.createStatement().executeUpdate("DROP TABLE fire_extinguishers");
    }

    private void insertData(FireExtinguisherData data) throws SQLException, DbException {
        fireExtinguisherDao.insert(data);
    }
}
