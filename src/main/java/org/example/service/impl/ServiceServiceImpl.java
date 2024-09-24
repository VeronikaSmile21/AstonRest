package org.example.service.impl;

import org.example.model.ServiceEntity;
import org.example.repository.ServiceEntityRepository;
import org.example.repository.impl.ServiceEntityRepositoryImpl;
import org.example.service.ServiceService;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {
    private ServiceEntityRepository serviceEntityRepository = new ServiceEntityRepositoryImpl();

    @Override
    public boolean deleteById(Integer id) {
        return serviceEntityRepository.deleteById(id);
    }

    @Override
    public ServiceEntity save(ServiceEntity serviceEntity) {
        return serviceEntityRepository.save(serviceEntity);
    }

    @Override
    public ServiceEntity findById(Integer id) {
        return serviceEntityRepository.findById(id);
    }

    @Override
    public List<ServiceEntity> findAll() {
        return serviceEntityRepository.findAll();
    }
}
