package org.example.service;

import org.example.model.ClientEntity;

import java.util.List;

public interface ClientService {
    boolean deleteById(Integer id);

    ClientEntity save(ClientEntity clientEntity);

    ClientEntity findById(Integer id);

    List<ClientEntity> findAll();
}
