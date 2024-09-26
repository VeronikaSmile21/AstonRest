package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.AnimalEntity;
import org.example.model.ServiceEntity;
import org.example.repository.AnimalEntityRepository;
import org.example.repository.mapper.AnimalResultSetMapper;
import org.example.repository.mapper.AnimalResultSetMapperImpl;
import org.example.repository.mapper.ServiceResultSetMapper;
import org.example.repository.mapper.ServiceResultSetMapperImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalEntityRepositoryImpl implements AnimalEntityRepository {
    private AnimalResultSetMapper resultSetMapper = new AnimalResultSetMapperImpl();
    private ServiceResultSetMapper serviceMapper = new ServiceResultSetMapperImpl();
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public AnimalEntity findById(Integer id) {
        // Здесь используем try with resources
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT a.id as a_id, a.name as a_name, a.price_coeff as a_price_coeff, " +
                            "s.id as s_id, s.name as s_name, s.price as s_price " +
                            "FROM `animal` as a " +
                            "inner join `service_animal` as sa on sa.animal_id = a.id " +
                            "inner join `service` as s on s.id = sa.service_id " +
                            "where a.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            AnimalEntity animalEntity = null;
            List<ServiceEntity> serviceEntities = new ArrayList<>();

            while (resultSet.next()) {
                if (animalEntity == null) {
                    animalEntity = resultSetMapper.map(resultSet);
                }
                serviceEntities.add(serviceMapper.map(resultSet));
            }
            animalEntity.setServices(serviceEntities);
            return animalEntity;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {

        try (Connection connection = connectionManager.getConnection()) {
            if (id > 0) {
                //update
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM animal WHERE id = ?");
                pstmt.setInt(1, id);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    return false;
                    //throw new SQLException("Creating user failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            return false;
            //throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public List<AnimalEntity> findAll() {
        List<AnimalEntity> result = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select a.id as a_id, a.name as a_name, a.price_coeff as a_price_coeff " +
                    " from animal as a");
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
    public AnimalEntity save(AnimalEntity animalEntity) {
        try (Connection connection = connectionManager.getConnection()) {

            if (animalEntity.getId() > 0) {
                //update
                PreparedStatement pstmt = connection.prepareStatement(
                        "UPDATE animal SET name = ?, price_coeff = ? WHERE id = ?");
                pstmt.setString(1, animalEntity.getName());
                pstmt.setDouble(2, animalEntity.getPriceCoeff());
                pstmt.setInt(3, animalEntity.getId());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

            } else {
                //insert
                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO animal (name, price_coeff) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, animalEntity.getName());
                pstmt.setDouble(2, animalEntity.getPriceCoeff());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        animalEntity.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return animalEntity;
    }
}
