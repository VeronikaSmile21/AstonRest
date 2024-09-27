package repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.db.ConnectionManager;
import org.example.model.ServiceEntity;
import org.example.repository.ServiceEntityRepository;
import org.example.repository.impl.ServiceEntityRepositoryImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestServiceRepository {

    static ServiceEntityRepository serviceEntityRepository;

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
        serviceEntityRepository = new ServiceEntityRepositoryImpl(connectionManager);

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
        List<ServiceEntity> serviceEntities = serviceEntityRepository.findAll();
        Assertions.assertEquals(4, serviceEntities.size());
        Assertions.assertEquals("Vaccination", serviceEntities.get(0).getName());
        Assertions.assertEquals("Chipping", serviceEntities.get(1).getName());
        Assertions.assertEquals("Castration", serviceEntities.get(2).getName());
        Assertions.assertEquals("Clinical_examination", serviceEntities.get(3).getName());
    }

    @Test
    public void testFindById() {
        ServiceEntity serviceEntity = serviceEntityRepository.findById(1);
        Assertions.assertEquals("Vaccination", serviceEntity.getName());
        //ManyToMany
        Assertions.assertEquals(2, serviceEntity.getAnimals().size());
    }

    @Test
    public void testInsert() {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Ultrasound_imaging");
        ServiceEntity saved = serviceEntityRepository.save(serviceEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Ultrasound_imaging", serviceEntity.getName());
        Assertions.assertEquals(5, serviceEntity.getId());
    }

    @Test
    public void testUpdate() {
        ServiceEntity serviceEntity = serviceEntityRepository.findById(2);
        Assertions.assertEquals("Castration", serviceEntity.getName());
        serviceEntity.setName("Dental_prosthesis");
        ServiceEntity saved = serviceEntityRepository.save(serviceEntity);
        Assertions.assertEquals("Dental_prosthesis", serviceEntity.getName());
        Assertions.assertEquals(2, serviceEntity.getId());

    }

    @Test
    public void testDelete() {
       List<ServiceEntity> serviceEntities = serviceEntityRepository.findAll();
       Assertions.assertEquals(3, serviceEntities.size());
       boolean result = serviceEntityRepository.deleteById(1);
       Assertions.assertTrue(result);
       List<ServiceEntity> serviceEntities2 = serviceEntityRepository.findAll();
       Assertions.assertEquals(2, serviceEntities2.size());
    }

    @AfterAll
    static void stopDb(){
        mySQLContainer.stop();
    }
}

