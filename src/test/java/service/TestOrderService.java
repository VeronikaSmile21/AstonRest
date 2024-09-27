package service;

import org.example.model.AnimalEntity;
import org.example.model.ClientEntity;
import org.example.model.OrderEntity;
import org.example.model.ServiceEntity;
import org.example.repository.OrderEntityRepository;
import org.example.service.OrderService;
import org.example.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {

    static OrderEntityRepository orderEntityRepository;

    static OrderService orderService;

    @BeforeAll
    public static void init() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(2);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(3);
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(4);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setDate(Date.valueOf("2024-05-19"));
        orderEntity.setStatus(2);
        orderEntity.setAnimal(animalEntity);
        orderEntity.setClient(clientEntity);
        orderEntity.setService(serviceEntity);

        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(2);
        orderEntity2.setDate(Date.valueOf("2024-05-10"));
        orderEntity2.setStatus(2);
        orderEntity2.setAnimal(animalEntity);
        orderEntity2.setClient(clientEntity);
        orderEntity2.setService(serviceEntity);

        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(orderEntity);
        orderEntities.add(orderEntity2);

        orderEntityRepository  = Mockito.mock(OrderEntityRepository.class);
        orderService = new OrderServiceImpl(orderEntityRepository);
        Mockito.when(orderEntityRepository.findAll()).thenReturn(orderEntities);
        Mockito.doReturn(orderEntity).when(orderEntityRepository).findById(1);
        Mockito.doReturn(orderEntity2).when(orderEntityRepository).save(Mockito.any());
        Mockito.doReturn(true).when(orderEntityRepository).deleteById(Mockito.any());
        Mockito.doReturn(orderEntities).when(orderEntityRepository).findByClientId(1);

    }

    @Test
    public void testFindAll() {
        List<OrderEntity> orderEntities = orderService.findAll();
        Assertions.assertEquals(2, orderEntities.size());
    }

    @Test
    public void testFindById() {
        OrderEntity orderEntity = orderService.findById(1);
        Assertions.assertEquals(Date.valueOf("2024-05-19"), orderEntity.getDate());
    }

    @Test
    public void testSave() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(2);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(3);
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(4);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(Date.valueOf("2024-05-10"));
        orderEntity.setId(2);
        orderEntity.setStatus(2);
        OrderEntity saved = orderService.save(orderEntity);
        Assertions.assertEquals(Date.valueOf("2024-05-10"), saved.getDate());
    }

    @Test
    public void testFindByClientId() {
        List<OrderEntity> orderEntities = orderService.findByClientId(1);
        Assertions.assertEquals(2, orderEntities.size());
    }

    @Test
    public void testDeleteById() {
        boolean result = orderService.deleteById(1);
        Assertions.assertTrue(result);
    }
}
