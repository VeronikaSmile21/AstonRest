package org.example.service.impl;

import org.example.model.AnimalEntity;
import org.example.repository.AnimalEntityRepository;
import org.example.repository.impl.AnimalEntityRepositoryImpl;
import org.example.service.AnimalService;

import java.util.List;

public class AnimalServiceImpl implements AnimalService {
    private AnimalEntityRepository animalEntityRepository;

    public AnimalServiceImpl() {
        this.animalEntityRepository = new AnimalEntityRepositoryImpl();
    }

    public AnimalServiceImpl(AnimalEntityRepository animalEntityRepository) {
        this.animalEntityRepository = animalEntityRepository;
    }

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
