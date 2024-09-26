package org.example.servlet.dto;

import java.util.ArrayList;
import java.util.List;

public class ServiceOutGoingDto {
    private int id;
    private String name;
    private float price;
    List<AnimalOutGoingDto> animals = new ArrayList<>();

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<AnimalOutGoingDto> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalOutGoingDto> animals) {
        this.animals = animals;
    }
}
