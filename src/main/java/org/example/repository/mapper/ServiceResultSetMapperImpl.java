package org.example.repository.mapper;

import org.example.model.ServiceEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceResultSetMapperImpl implements ServiceResultSetMapper {
    @Override
    public ServiceEntity map(ResultSet resultSet) throws SQLException {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName(resultSet.getString("name"));
        serviceEntity.setId(resultSet.getInt("id"));
        serviceEntity.setPrice(resultSet.getFloat("price"));
        return serviceEntity;
    }
}
