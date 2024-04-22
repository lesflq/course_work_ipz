package com.example.trytosmth.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;

public class DbManagerTest {

    @Mock
    private DbManager dbManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getInstanceTest() {
        dbManager = DbManager.getInstance();
        assertNotNull(dbManager);
    }

}