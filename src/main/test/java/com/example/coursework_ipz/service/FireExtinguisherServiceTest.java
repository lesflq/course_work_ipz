package com.example.coursework_ipz.service;

import com.example.coursework_ipz.model.FireExtinguisherData;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FireExtinguisherServiceTest {

    private FireExtinguisherService service;
    private List<FireExtinguisherData> mockData;

    @Before
    public void setUp() {
        service = new FireExtinguisherService();
        mockData = new ArrayList<>();

        // Додавання тестових даних
        mockData.add(new FireExtinguisherData("Location1", LocalDate.now().minusDays(1))); // прострочений
        mockData.add(new FireExtinguisherData("Location2", LocalDate.now().plusDays(1))); // не прострочений
        service.getFireExtinguishers().addAll(mockData);
    }

    @Test
    public void testIsExpired() {
        assertTrue(service.isExpired(LocalDate.now().minusDays(1))); // Вчора
        assertFalse(service.isExpired(LocalDate.now().plusDays(1))); // Завтра
    }

    @Test
    public void testReplaceExpiredFireExtinguishers() {
        service.replaceExpiredFireExtinguishers();
        for (FireExtinguisherData extinguisher : service.getFireExtinguishers()) {
            assertFalse(service.isExpired(extinguisher.getExpirationDate())); // Жоден не має бути простроченим
        }
    }



    @Test
    public void testReplaceExpiredFireExtinguisher() {
        List<FireExtinguisherData> updatedList = service.replaceExpiredFireExtinguisher();
        for (FireExtinguisherData extinguisher : updatedList) {
            assertFalse(service.isExpired(extinguisher.getExpirationDate())); // Жоден не має бути простроченим
        }
    }
}