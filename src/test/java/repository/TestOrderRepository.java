package repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.db.ConnectionManager;
import org.example.model.AnimalEntity;
import org.example.model.ClientEntity;
import org.example.model.OrderEntity;
import org.example.model.ServiceEntity;
import org.example.repository.OrderEntityRepository;
import org.example.repository.impl.OrderEntityRepositoryImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestOrderRepository {

    static OrderEntityRepository orderEntityRepository;

    @ClassRule
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.7")
            .withUsername("username")
            .withPassword("password")
            .withDatabaseName("vet_clinic");

    public static ConnectionManager connectionManager;


    @BeforeAll
    public static void setUp() {
        mySQLContainer.start();
        connectionManager = new ConnectionManagerTestImpl(mySQLContainer.getJdbcUrl(),
                mySQLContainer.getUsername(), mySQLContainer.getPassword());
        orderEntityRepository = new OrderEntityRepositoryImpl(connectionManager);

    }

    @BeforeEach
    public void setUpData() throws SQLException, IOException {

        String path = Paths.get("./src/main/resources/vet_clinic_migration.sql").toAbsolutePath().toString();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager.getConnection());
        scriptRunner.setSendFullScript(false);
        scriptRunner.setStopOnError(true);
        scriptRunner.runScript(new java.io.FileReader(path));


    }

    @Test
    public void containerRunning() {
        assertTrue(mySQLContainer.isRunning());
    }

    @Test
    public void testFindAll() {
        List<OrderEntity> orderEntities = orderEntityRepository.findAll();
        Assertions.assertEquals(3, orderEntities.size());
        Assertions.assertEquals(Date.valueOf("2024-01-02"), orderEntities.get(0).getDate());
        Assertions.assertEquals(Date.valueOf("2024-01-02"), orderEntities.get(1).getDate());
        Assertions.assertEquals(Date.valueOf("2024-01-02"), orderEntities.get(2).getDate());

    }

    @Test
    public void testFindById() {
        OrderEntity orderEntity = orderEntityRepository.findById(3);
        Assertions.assertEquals(Date.valueOf("2024-01-02"), orderEntity.getDate());

        // OneToMany
        Assertions.assertNotNull(orderEntity.getClient());
        Assertions.assertNotNull(orderEntity.getService());
        Assertions.assertNotNull(orderEntity.getAnimal());
        Assertions.assertEquals("Jessica", orderEntity.getClient().getName());
        Assertions.assertEquals("Snake", orderEntity.getAnimal().getName());
        Assertions.assertEquals("Chipping", orderEntity.getService().getName());
    }

    @Test
    public void testInsert() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setClient(new ClientEntity());
        orderEntity.getClient().setId(4);
        orderEntity.setAnimal(new AnimalEntity());
        orderEntity.getAnimal().setId(4);
        orderEntity.setService(new ServiceEntity());
        orderEntity.getService().setId(4);
        orderEntity.setDate(Date.valueOf("2024-02-05"));
        OrderEntity saved = orderEntityRepository.save(orderEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(Date.valueOf("2024-02-05"), orderEntity.getDate());
        Assertions.assertEquals(4, orderEntity.getId());
    }

    @Test
    public void testUpdate() {
        OrderEntity orderEntity = orderEntityRepository.findById(1);
        Assertions.assertEquals(1, orderEntity.getStatus());
        orderEntity.setStatus(2);
        OrderEntity saved = orderEntityRepository.save(orderEntity);
        Assertions.assertEquals(2, orderEntity.getStatus());

    }

    @Test
    public void testDelete() {
       List<OrderEntity> orderEntities = orderEntityRepository.findAll();
       Assertions.assertEquals(3, orderEntities.size());
       boolean result = orderEntityRepository.deleteById(1);
       Assertions.assertTrue(result);
       List<OrderEntity> orderEntities2 = orderEntityRepository.findAll();
       Assertions.assertEquals(2, orderEntities2.size());
    }

    @AfterAll
    static void stopDb(){
        mySQLContainer.stop();
    }
}

