package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.ClientEntity;
import org.example.repository.ClientEntityRepository;
import org.example.repository.mapper.ClientResultSetMapper;
import org.example.repository.mapper.ClientResultSetMapperImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientEntityRepositoryImpl implements ClientEntityRepository {
    private ClientResultSetMapper resultSetMapper = new ClientResultSetMapperImpl();
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public ClientEntity findById(Integer id) {
        // Здесь используем try with resources
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSetMapper.map(resultSet);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {

        try (Connection connection = connectionManager.getConnection()) {
            if (id > 0) {
                //update
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM client WHERE id = ?");
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
    public List<ClientEntity> findAll() {
        List<ClientEntity> result = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from client");
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
    public ClientEntity save(ClientEntity clientEntity) {
        try (Connection connection = connectionManager.getConnection()) {

            if (clientEntity.getId() > 0) {
                //update
                PreparedStatement pstmt = connection.prepareStatement(
                        "UPDATE client SET name = ?, phone = ? WHERE id = ?");
                pstmt.setString(1, clientEntity.getName());
                pstmt.setString(2, clientEntity.getPhone());
                pstmt.setInt(3, clientEntity.getId());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

            } else {
                //insert
                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO client (name, phone) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, clientEntity.getName());
                pstmt.setString(2, clientEntity.getPhone());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        clientEntity.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientEntity;
    }
}
