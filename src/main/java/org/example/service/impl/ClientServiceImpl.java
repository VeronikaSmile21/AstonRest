package org.example.service.impl;

import org.example.model.ClientEntity;
import org.example.repository.ClientEntityRepository;
import org.example.repository.impl.ClientEntityRepositoryImpl;
import org.example.service.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private ClientEntityRepository clientEntityRepository = new ClientEntityRepositoryImpl();

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
        return clientEntityRepository.findById(id);
    }

    @Override
    public List<ClientEntity> findAll() {
        return clientEntityRepository.findAll();
    }
}
