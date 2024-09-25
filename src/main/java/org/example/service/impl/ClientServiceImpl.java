package org.example.service.impl;

import org.example.model.ClientEntity;
import org.example.model.OrderEntity;
import org.example.repository.ClientEntityRepository;
import org.example.repository.impl.ClientEntityRepositoryImpl;
import org.example.service.ClientService;
import org.example.service.OrderService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private ClientEntityRepository clientEntityRepository = new ClientEntityRepositoryImpl();
    private OrderService orderService = new OrderServiceImpl();

    @Override
    public boolean deleteById(Integer id) {
        return clientEntityRepository.deleteById(id);
    }

    @Override
    public ClientEntity save(ClientEntity clientEntity) {
        return clientEntityRepository.save(clientEntity);
    }

    @Override
    public ClientEntity findById(Integer id) {
        ClientEntity clientEntity = clientEntityRepository.findById(id);
        List<OrderEntity> orders = orderService.findByClientId(id);
        clientEntity.setOrders(orders);
        return clientEntity;
    }

    @Override
    public List<ClientEntity> findAll() {
        return clientEntityRepository.findAll();
    }
}
