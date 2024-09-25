package org.example.repository.mapper;

import org.example.model.AnimalEntity;
import org.example.model.OrderEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderResultSetMapperImpl implements OrderResultSetMapper {

    AnimalResultSetMapper animalResultSetMapper = new AnimalResultSetMapperImpl();
    ClientResultSetMapper clientResultSetMapper = new ClientResultSetMapperImpl();
    ServiceResultSetMapper serviceResultSetMapper = new ServiceResultSetMapperImpl();

    @Override
    public OrderEntity map(ResultSet resultSet) throws SQLException {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(resultSet.getInt("o_id"));
        orderEntity.setDate(resultSet.getDate( "o_date"));
        orderEntity.setStatus(resultSet.getInt( "o_status"));
        orderEntity.setCost(resultSet.getFloat( "o_cost"));


        orderEntity.setAnimal(animalResultSetMapper.map(resultSet));
        orderEntity.setClient(clientResultSetMapper.map(resultSet));
        orderEntity.setService(serviceResultSetMapper.map(resultSet));

        return orderEntity;
    }
}
