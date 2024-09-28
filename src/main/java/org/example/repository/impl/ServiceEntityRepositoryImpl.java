package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.AnimalEntity;
import org.example.model.ServiceEntity;
import org.example.repository.ServiceEntityRepository;
import org.example.repository.mapper.AnimalResultSetMapper;
import org.example.repository.mapper.AnimalResultSetMapperImpl;
import org.example.repository.mapper.ServiceResultSetMapper;
import org.example.repository.mapper.ServiceResultSetMapperImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEntityRepositoryImpl implements ServiceEntityRepository {
    private ServiceResultSetMapper resultSetMapper = new ServiceResultSetMapperImpl();
    private AnimalResultSetMapper animalMapper = new AnimalResultSetMapperImpl();
    private ConnectionManager connectionManager;

    public ServiceEntityRepositoryImpl() {
        this.connectionManager = ConnectionManagerImpl.getInstance();
    }

    public ServiceEntityRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public ServiceEntity findById(Integer id) {
        // Здесь используем try with resources
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT a.id as a_id, a.name as a_name, a.price_coeff as a_price_coeff, " +
                            "s.id as s_id, s.name as s_name, s.price as s_price " +
                            "FROM `animal` as a " +
                            "inner join `service_animal` as sa on sa.animal_id = a.id " +
                            "inner join `service` as s on s.id = sa.service_id " +
                            "where s.id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            ServiceEntity serviceEntity = null;
            List<AnimalEntity> animalEntities = new ArrayList<>();

            while (resultSet.next()) {
                if (serviceEntity == null) {
                    serviceEntity = resultSetMapper.map(resultSet);
                }
                animalEntities.add(animalMapper.map(resultSet));
            }
            serviceEntity.setAnimals(animalEntities);
            return serviceEntity;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {

        try (Connection connection = connectionManager.getConnection()) {
            if (id > 0) {
                //update
                try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM service WHERE id = ?")) {
                    pstmt.setInt(1, id);
                    int affectedRows = pstmt.executeUpdate();

                    if (affectedRows == 0) {
                        return false;
                        //throw new SQLException("Creating user failed, no rows affected.");
                    }
                }
            }
        } catch (SQLException e) {
            return false;
            //throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public List<ServiceEntity> findAll() {
        List<ServiceEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select s.id as s_id, s.name as s_name, s.price as s_price from service as s")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSetMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public ServiceEntity save(ServiceEntity serviceEntity) {
        try (Connection connection = connectionManager.getConnection()) {

            if (serviceEntity.getId() > 0) {
                //update
                try(PreparedStatement pstmt = connection.prepareStatement(
                        "UPDATE service SET name = ?, price = ? WHERE id = ?")) {
                    pstmt.setString(1, serviceEntity.getName());
                    pstmt.setFloat(2, serviceEntity.getPrice());
                    pstmt.setInt(3, serviceEntity.getId());
                    int affectedRows = pstmt.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Creating user failed, no rows affected.");
                    }
                }
            } else {
                //insert
                try (PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO service (name, price) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, serviceEntity.getName());
                    pstmt.setFloat(2, serviceEntity.getPrice());
                    int affectedRows = pstmt.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Creating user failed, no rows affected.");
                    }

                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            serviceEntity.setId(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException("Creating user failed, no ID obtained.");
                        }
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return serviceEntity;
    }
}
