package repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.AnimalEntity;
import org.example.repository.AnimalEntityRepository;
import org.example.repository.impl.AnimalEntityRepositoryImpl;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestAnimalRepository {

    static AnimalEntityRepository animalEntityRepository;

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
        animalEntityRepository = new AnimalEntityRepositoryImpl(connectionManager);

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
        List<AnimalEntity> animalEntities = animalEntityRepository.findAll();
        Assertions.assertEquals(3, animalEntities.size());
        Assertions.assertEquals("Cat", animalEntities.get(0).getName());
        Assertions.assertEquals("Dog", animalEntities.get(1).getName());
        Assertions.assertEquals("Snake", animalEntities.get(2).getName());
    }

    @Test
    public void testFindById() {
        AnimalEntity animalEntity = animalEntityRepository.findById(1);
        Assertions.assertEquals("Cat", animalEntity.getName());
    }

    @Test
    public void testInsert() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName("Pig");
        AnimalEntity saved = animalEntityRepository.save(animalEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Pig", animalEntity.getName());
        Assertions.assertEquals(4, animalEntity.getId());
    }

    @Test
    public void testUpdate() {
        AnimalEntity animalEntity = animalEntityRepository.findById(1);
        Assertions.assertEquals("Cat", animalEntity.getName());
        animalEntity.setName("Pig");
        AnimalEntity saved = animalEntityRepository.save(animalEntity);
        Assertions.assertEquals("Pig", animalEntity.getName());
        Assertions.assertEquals(1, animalEntity.getId());

    }

    @Test
    public void testDelete() {
       List<AnimalEntity> animalEntities = animalEntityRepository.findAll();
       Assertions.assertEquals(3, animalEntities.size());
       boolean result = animalEntityRepository.deleteById(1);
       Assertions.assertTrue(result);
       List<AnimalEntity> animalEntities2 = animalEntityRepository.findAll();
       Assertions.assertEquals(2, animalEntities2.size());
    }

    @AfterAll
    static void stopDb(){
        mySQLContainer.stop();
    }
}

