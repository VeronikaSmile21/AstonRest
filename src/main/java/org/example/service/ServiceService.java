package org.example.service;

import org.example.model.ServiceEntity;

import java.util.List;

public interface ServiceService {
    boolean deleteById(Integer id);

    ServiceEntity save(ServiceEntity serviceEntity);

    ServiceEntity findById(Integer id);

    List<ServiceEntity> findAll();
}
