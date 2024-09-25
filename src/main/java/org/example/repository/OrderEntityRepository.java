package org.example.repository;

import org.example.model.OrderEntity;

import java.util.List;

public interface OrderEntityRepository extends SimpleRepository<OrderEntity, Integer> {
    List<OrderEntity> findByClientId(int clientId);
}
