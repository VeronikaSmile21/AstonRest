package org.example.servlet.dto;

public class AnimalIncomingDto {
    private String id;
    private String name;
    private String priceCoeff;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPriceCoeff() {
        return priceCoeff;
    }

    public void setPriceCoeff(String priceCoeff) {
        this.priceCoeff = priceCoeff;
    }
}
