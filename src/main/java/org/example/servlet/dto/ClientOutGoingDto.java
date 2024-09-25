package org.example.servlet.dto;

import org.example.model.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class ClientOutGoingDto {
    private int id;
    private String name;
    private String phone;
    private List<OrderOutGoingDto> orders = new ArrayList<>();

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderOutGoingDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderOutGoingDto> orders) {
        this.orders = orders;
    }
}
