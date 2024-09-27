package service;

import org.example.model.AnimalEntity;
import org.example.repository.AnimalEntityRepository;
import org.example.service.AnimalService;
import org.example.service.impl.AnimalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestAnimalService {

    static AnimalEntityRepository animalEntityRepository;

    static AnimalService animalService;

    @BeforeAll
    public static void init() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName("Cat");
        animalEntity.setId(1);
        animalEntity.setPriceCoeff(2);

        AnimalEntity animalEntity2 = new AnimalEntity();
        animalEntity2.setName("Dog");
        animalEntity2.setId(2);
        animalEntity2.setPriceCoeff(3);

        List<AnimalEntity> animalEntities = new ArrayList<>();
        animalEntities.add(animalEntity);
        animalEntities.add(animalEntity2);

        animalEntityRepository  = Mockito.mock(AnimalEntityRepository.class);
        animalService = new AnimalServiceImpl(animalEntityRepository);
        Mockito.when(animalEntityRepository.findAll()).thenReturn(animalEntities);
        Mockito.doReturn(animalEntity).when(animalEntityRepository).findById(1);
        Mockito.doReturn(animalEntity2).when(animalEntityRepository).save(Mockito.any());
        Mockito.doReturn(true).when(animalEntityRepository).deleteById(Mockito.any());

    }

    @Test
    public void testFindAll() {
        List<AnimalEntity> animalEntities = animalService.findAll();
        Assertions.assertEquals(2, animalEntities.size());
    }

    @Test
    public void testFindById() {
        AnimalEntity animalEntity = animalService.findById(1);
        Assertions.assertEquals("Cat", animalEntity.getName());
    }

    @Test
    public void testSave() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName("Dog");
        animalEntity.setId(2);
        animalEntity.setPriceCoeff(2);
        AnimalEntity saved = animalService.save(animalEntity);
        Assertions.assertEquals("Dog", saved.getName());
    }

    @Test
    public void testDeleteById() {
        boolean result = animalService.deleteById(1);
        Assertions.assertTrue(result);
    }
}
