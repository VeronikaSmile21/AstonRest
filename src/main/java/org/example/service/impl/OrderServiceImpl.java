package org.example.service.impl;

import org.example.model.OrderEntity;
import org.example.repository.OrderEntityRepository;
import org.example.repository.impl.OrderEntityRepositoryImpl;
import org.example.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderEntityRepository orderEntityRepository = new OrderEntityRepositoryImpl();

    @Override
    public boolean deleteById(Integer id) {
        return orderEntityRepository.deleteById(id);
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return orderEntityRepository.save(orderEntity);
    }

    @Override
    public OrderEntity findById(Integer id) {
        return orderEntityRepository.findById(id);
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderEntityRepository.findAll();
    }

    @Override
    public List<OrderEntity> findByClientId(Integer clientId) {
        return orderEntityRepository.findByClientId(clientId);
    }
}
