package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class AnimalEntity {
    private int id;
    private String name;
    private float priceCoeff;
    List<ServiceEntity> services = new ArrayList<ServiceEntity>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public float getPriceCoeff() {
        return priceCoeff;
    }

    public void setPriceCoeff(float priceCoeff) {
        this.priceCoeff = priceCoeff;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }
}
