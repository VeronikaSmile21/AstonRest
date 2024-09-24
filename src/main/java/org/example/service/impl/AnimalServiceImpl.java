package org.example.service.impl;

import org.example.model.AnimalEntity;
import org.example.repository.AnimalEntityRepository;
import org.example.repository.impl.AnimalEntityRepositoryImpl;
import org.example.service.AnimalService;

import java.util.List;
import java.util.UUID;

public class AnimalServiceImpl implements AnimalService {
    private AnimalEntityRepository animalEntityRepository = new AnimalEntityRepositoryImpl();

    @Override
    public boolean deleteById(Integer id) {
        return animalEntityRepository.deleteById(id);
    }

    @Override
    public AnimalEntity save(AnimalEntity animalEntity) {
        return animalEntityRepository.save(animalEntity);
    }

    @Override
    public AnimalEntity findById(Integer id) {
        return animalEntityRepository.findById(id);
    }

    @Override
    public List<AnimalEntity> findAll() {
        return animalEntityRepository.findAll();
    }
}
