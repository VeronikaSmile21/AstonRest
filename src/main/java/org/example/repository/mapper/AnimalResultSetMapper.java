package org.example.repository.mapper;

import org.example.model.AnimalEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AnimalResultSetMapper {
    AnimalEntity map(ResultSet resultSet) throws SQLException;
}
