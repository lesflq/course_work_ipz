package com.example.trytosmth.service;// FireExtinguisherModel.java

import com.example.trytosmth.dao.FireExtinguisherDao;
import com.example.trytosmth.dao.exception.DbException;
import com.example.trytosmth.model.FireExtinguisherData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FireExtinguisherService {

    private ObservableList<FireExtinguisherData> fireExtinguishers = FXCollections.observableArrayList();

    public ObservableList<FireExtinguisherData> getFireExtinguishers() {
        return fireExtinguishers;
    }

    private final FireExtinguisherDao fireExtinguisherDao = new FireExtinguisherDao();

    public List<FireExtinguisherData> getAll() {
        try {
            return fireExtinguisherDao.getAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean isExpired(LocalDate expirationDate) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(expirationDate);
    }

    public void replaceExpiredFireExtinguishers() {
        for (FireExtinguisherData extinguisher : fireExtinguishers) {
            if (isExpired(extinguisher.getExpirationDate())) {
                extinguisher.setExpirationDate(LocalDate.now().plusYears(1));
            }
        }
    }
    public List<FireExtinguisherData> delete(FireExtinguisherData extinguisher) throws DbException {
        return fireExtinguisherDao.delete(extinguisher);
    }

    public void update(FireExtinguisherData extinguisher) {
        try {
            fireExtinguisherDao.update(extinguisher);
        } catch(DbException e) {
            e.printStackTrace();
        }

    }

    public void insert(FireExtinguisherData newExtinguisher) throws DbException {
        fireExtinguisherDao.insert(newExtinguisher);
    }
}
