package org.example.servlet.dto;

import org.example.model.ServiceEntity;

import java.util.ArrayList;
import java.util.List;

public class AnimalOutGoingDto {
    private int id;
    private String name;
    private float priceCoeff;
    List<ServiceOutGoingDto> services = new ArrayList<>();

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

    public List<ServiceOutGoingDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceOutGoingDto> services) {
        this.services = services;
    }
}
