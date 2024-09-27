package repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.db.ConnectionManager;
import org.example.model.ClientEntity;
import org.example.repository.ClientEntityRepository;
import org.example.repository.impl.ClientEntityRepositoryImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestClientRepository {

    static ClientEntityRepository clientEntityRepository;

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
        clientEntityRepository = new ClientEntityRepositoryImpl(connectionManager);

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
        List<ClientEntity> clientEntities = clientEntityRepository.findAll();
        Assertions.assertEquals(4, clientEntities.size());
        Assertions.assertEquals("Alex", clientEntities.get(0).getName());
        Assertions.assertEquals("Jessica", clientEntities.get(1).getName());
        Assertions.assertEquals("John", clientEntities.get(2).getName());
        Assertions.assertEquals("Oliver", clientEntities.get(3).getName());
    }

    @Test
    public void testFindById() {
        ClientEntity clientEntity = clientEntityRepository.findById(2);
        Assertions.assertEquals("Jessica", clientEntity.getName());
    }

    @Test
    public void testInsert() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName("Mary");
        ClientEntity saved = clientEntityRepository.save(clientEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Mary", clientEntity.getName());
        Assertions.assertEquals(8, clientEntity.getId());
    }

    @Test
    public void testUpdate() {
        ClientEntity clientEntity = clientEntityRepository.findById(1);
        Assertions.assertEquals("Alex", clientEntity.getName());
        clientEntity.setName("Alice");
        ClientEntity saved = clientEntityRepository.save(clientEntity);
        Assertions.assertEquals("Alice", clientEntity.getName());
        Assertions.assertEquals(1, clientEntity.getId());

    }

    @Test
    public void testDelete() {
       List<ClientEntity> clientEntities = clientEntityRepository.findAll();
       Assertions.assertEquals(4, clientEntities.size());
       boolean result = clientEntityRepository.deleteById(1);
       Assertions.assertTrue(result);
       List<ClientEntity> clientEntities2 = clientEntityRepository.findAll();
       Assertions.assertEquals(3, clientEntities2.size());
    }

    @AfterAll
    static void stopDb(){
        mySQLContainer.stop();
    }
}

