package org.example.servlet.dto;

public class OrderOutGoingDto {
    private int id;
    private ClientOutGoingDto client;
    private ServiceOutGoingDto service;
    private AnimalOutGoingDto animal;
    private String date;
    private int status;
    private float cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientOutGoingDto getClient() {
        return client;
    }

    public void setClient(ClientOutGoingDto client) {
        this.client = client;
    }

    public ServiceOutGoingDto getService() {
        return service;
    }

    public void setService(ServiceOutGoingDto service) {
        this.service = service;
    }

    public AnimalOutGoingDto getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalOutGoingDto animal) {
        this.animal = animal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
