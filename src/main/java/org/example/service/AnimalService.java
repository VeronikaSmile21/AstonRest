package org.example.service;

import org.example.model.AnimalEntity;

import java.util.List;
import java.util.UUID;

public interface AnimalService {
    boolean deleteById(Integer id);

    AnimalEntity save(AnimalEntity animalEntity);

    AnimalEntity findById(Integer id);

    List<AnimalEntity> findAll();
}
