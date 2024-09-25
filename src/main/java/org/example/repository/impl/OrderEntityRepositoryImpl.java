package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.OrderEntity;
import org.example.repository.OrderEntityRepository;
import org.example.repository.mapper.OrderResultSetMapper;
import org.example.repository.mapper.OrderResultSetMapperImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderEntityRepositoryImpl implements OrderEntityRepository {
    private OrderResultSetMapper resultSetMapper = new OrderResultSetMapperImpl();
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public OrderEntity findById(Integer id) {
        // Здесь используем try with resources
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT o.id as o_id, o.date as o_date, o.status as o_status, o.cost as o_cost, " +
                            "a.id as a_id, a.name as a_name, a.price_coeff as a_price_coeff, " +
                            "c.id as c_id, c.name as c_name, c.phone as c_phone, " +
                            "s.id as s_id, s.name as s_name, s.price as s_price " +
                            "FROM `order` as o " +
                            "inner join `animal` as a on o.animal_id = a.id " +
                            "inner join `client` as c on o.client_id = c.id " +
                            "inner join `service` as s on o.service_id = s.id " +
                             "where o.id = ? ");
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
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM `order` WHERE id = ?");
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
    public List<OrderEntity> findAll() {
        List<OrderEntity> result = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT o.id as o_id, o.date as o_date, o.status as o_status, o.cost as o_cost, " +
                            "a.id as a_id, a.name as a_name, a.price_coeff as a_price_coeff, " +
                            "c.id as c_id, c.name as c_name, c.phone as c_phone, " +
                            "s.id as s_id, s.name as s_name, s.price as s_price " +
                            "FROM `order` as o " +
                            "inner join `animal` as a on o.animal_id = a.id " +
                            "inner join `client` as c on o.client_id = c.id " +
                            "inner join `service` as s on o.service_id = s.id");
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
    public OrderEntity save(OrderEntity orderEntity) {
        try (Connection connection = connectionManager.getConnection()) {

            if (orderEntity.getId() > 0) {
                //update
                PreparedStatement pstmt = connection.prepareStatement(
                        "UPDATE `order` SET client_id = ?, service_id = ?, animal_id = ?, date = ?, status = ?, cost = ? WHERE id = ?");
                pstmt.setInt(1, orderEntity.getClient().getId());
                pstmt.setInt(2, orderEntity.getService().getId());
                pstmt.setInt(3, orderEntity.getAnimal().getId());
                pstmt.setDate(4, orderEntity.getDate());
                pstmt.setInt(5, orderEntity.getStatus());
                pstmt.setFloat(6, orderEntity.getCost());
                pstmt.setInt(7, orderEntity.getId());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

            } else {
                //insert
                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO `order` (client_id, service_id, animal_id, date, status, cost) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, orderEntity.getClient().getId());
                pstmt.setInt(2, orderEntity.getService().getId());
                pstmt.setInt(3, orderEntity.getAnimal().getId());
                pstmt.setDate(4, orderEntity.getDate());
                pstmt.setInt(5, orderEntity.getStatus());
                pstmt.setFloat(6, orderEntity.getCost());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderEntity.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderEntity;
    }

    @Override
    public List<OrderEntity> findByClientId(int clientId) {
        List<OrderEntity> result = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT o.id as o_id, o.date as o_date, o.status as o_status, o.cost as o_cost, " +
                            "a.id as a_id, a.name as a_name, a.price_coeff as a_price_coeff, " +
                            "c.id as c_id, c.name as c_name, c.phone as c_phone, " +
                            "s.id as s_id, s.name as s_name, s.price as s_price " +
                            "FROM `order` as o " +
                            "inner join `animal` as a on o.animal_id = a.id " +
                            "inner join `client` as c on o.client_id = c.id " +
                            "inner join `service` as s on o.service_id = s.id " +
                            "where c.id = ?");
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSetMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
