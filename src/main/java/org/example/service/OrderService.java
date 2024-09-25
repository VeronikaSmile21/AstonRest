package org.example.service;

import org.example.model.OrderEntity;

import java.util.List;

public interface OrderService {
    boolean deleteById(Integer id);

    OrderEntity save(OrderEntity orderEntity);

    OrderEntity findById(Integer id);

    List<OrderEntity> findAll();

    List<OrderEntity> findByClientId(Integer clientId);
}
