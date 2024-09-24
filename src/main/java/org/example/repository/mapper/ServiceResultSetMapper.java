package org.example.repository.mapper;

import org.example.model.ServiceEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ServiceResultSetMapper {
    ServiceEntity map(ResultSet resultSet) throws SQLException;
}
