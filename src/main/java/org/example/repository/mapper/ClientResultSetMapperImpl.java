package org.example.repository.mapper;

import org.example.model.ClientEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientResultSetMapperImpl implements ClientResultSetMapper {
    @Override
    public ClientEntity map(ResultSet resultSet) throws SQLException {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(resultSet.getString("c_name"));
        clientEntity.setId(resultSet.getInt("c_id"));
        clientEntity.setPhone(resultSet.getString("c_phone"));
        return clientEntity;
    }
}
