package org.example.repository.mapper;

import org.example.model.OrderEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderResultSetMapperImpl implements OrderResultSetMapper {
    @Override
    public OrderEntity map(ResultSet resultSet) throws SQLException {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setClientId(resultSet.getInt("client_id"));
        orderEntity.setServiceId(resultSet.getInt("service_id"));
        orderEntity.setAnimalId(resultSet.getInt("animal_id"));
        orderEntity.setDate(resultSet.getInt("date"));
        orderEntity.setStatus(resultSet.getInt("status"));
        orderEntity.setCost(resultSet.getFloat("cost"));
        return orderEntity;
    }
}
