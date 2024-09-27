package service;

import org.example.model.ServiceEntity;
import org.example.repository.ServiceEntityRepository;
import org.example.service.ServiceService;
import org.example.service.impl.ServiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestServiceService {

    static ServiceEntityRepository serviceEntityRepository;

    static ServiceService serviceService;

    @BeforeAll
    public static void init() {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Vaccination");
        serviceEntity.setId(1);
        serviceEntity.setPrice(300);

        ServiceEntity serviceEntity2 = new ServiceEntity();
        serviceEntity2.setName("Ultrasound_imaging");
        serviceEntity2.setId(2);
        serviceEntity2.setPrice(200);

        List<ServiceEntity> serviceEntities = new ArrayList<>();
        serviceEntities.add(serviceEntity);
        serviceEntities.add(serviceEntity2);

        serviceEntityRepository  = Mockito.mock(ServiceEntityRepository.class);
        serviceService = new ServiceServiceImpl(serviceEntityRepository);
        Mockito.when(serviceEntityRepository.findAll()).thenReturn(serviceEntities);
        Mockito.doReturn(serviceEntity).when(serviceEntityRepository).findById(1);
        Mockito.doReturn(serviceEntity2).when(serviceEntityRepository).save(Mockito.any());
        Mockito.doReturn(true).when(serviceEntityRepository).deleteById(Mockito.any());

    }

    @Test
    public void testFindAll() {
        List<ServiceEntity> serviceEntities = serviceService.findAll();
        Assertions.assertEquals(2, serviceEntities.size());
    }

    @Test
    public void testFindById() {
        ServiceEntity serviceEntity = serviceService.findById(1);
        Assertions.assertEquals("Vaccination", serviceEntity.getName());
    }

    @Test
    public void testSave() {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Ultrasound_imaging");
        serviceEntity.setId(2);
        serviceEntity.setPrice(200);
        ServiceEntity saved = serviceService.save(serviceEntity);
        Assertions.assertEquals("Ultrasound_imaging", saved.getName());
    }

    @Test
    public void testDeleteById() {
        boolean result = serviceService.deleteById(1);
        Assertions.assertTrue(result);
    }
}
