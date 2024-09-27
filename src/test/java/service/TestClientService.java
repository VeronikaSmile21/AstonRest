package service;

import org.example.model.ClientEntity;
import org.example.model.OrderEntity;
import org.example.repository.ClientEntityRepository;
import org.example.repository.OrderEntityRepository;
import org.example.service.ClientService;
import org.example.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestClientService {

    static ClientEntityRepository clientEntityRepository;
    static OrderEntityRepository orderEntityRepository;

    static ClientService clientService;

    @BeforeAll
    public static void init() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName("Alice");
        clientEntity.setId(1);
        clientEntity.setPhone("252628");

        ClientEntity clientEntity2 = new ClientEntity();
        clientEntity2.setName("Rick");
        clientEntity2.setId(2);
        clientEntity2.setPhone("252629");

        List<ClientEntity> clientEntities = new ArrayList<>();
        clientEntities.add(clientEntity);
        clientEntities.add(clientEntity2);

        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(new OrderEntity());

        clientEntityRepository  = Mockito.mock(ClientEntityRepository.class);
        orderEntityRepository = Mockito.mock(OrderEntityRepository.class);
        clientService = new ClientServiceImpl(clientEntityRepository, orderEntityRepository);
        Mockito.when(clientEntityRepository.findAll()).thenReturn(clientEntities);
        Mockito.doReturn(clientEntity).when(clientEntityRepository).findById(1);
        Mockito.doReturn(orderEntities).when(orderEntityRepository).findByClientId(1);
        Mockito.doReturn(clientEntity2).when(clientEntityRepository).save(Mockito.any());
        Mockito.doReturn(true).when(clientEntityRepository).deleteById(Mockito.any());

    }

    @Test
    public void testFindAll() {
        List<ClientEntity> clientEntities = clientService.findAll();
        Assertions.assertEquals(2, clientEntities.size());
    }

    @Test
    public void testFindById() {
        ClientEntity clientEntity = clientService.findById(1);
        Assertions.assertEquals("Alice", clientEntity.getName());
        // OneToMany
        Assertions.assertEquals(1, clientEntity.getOrders().size());
    }

    @Test
    public void testSave() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName("Rick");
        clientEntity.setId(2);
        clientEntity.setPhone("458964");
        ClientEntity saved = clientService.save(clientEntity);
        Assertions.assertEquals("Rick", saved.getName());
    }

    @Test
    public void testDeleteById() {
        boolean result = clientService.deleteById(1);
        Assertions.assertTrue(result);
    }
}
