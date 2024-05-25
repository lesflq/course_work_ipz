package com.example.coursework_ipz.model;

import java.time.LocalDate;
import java.util.Objects;

public class FireExtinguisherData {

    private Long id;
    private String location;
    private LocalDate expirationDate;

    public FireExtinguisherData() {}

    public FireExtinguisherData(String location, LocalDate expirationDate) {
        this.location = location;
        this.expirationDate = expirationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireExtinguisherData that = (FireExtinguisherData) o;
        return Objects.equals(id, that.id) && Objects.equals(location, that.location) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, expirationDate);
    }

    public void setId(Long id) {
        this.id = id;
    }

}