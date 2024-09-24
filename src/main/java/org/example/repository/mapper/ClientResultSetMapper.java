package org.example.repository.mapper;

import org.example.model.ClientEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClientResultSetMapper {
    ClientEntity map(ResultSet resultSet) throws SQLException;
}
