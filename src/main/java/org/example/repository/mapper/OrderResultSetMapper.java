package org.example.repository.mapper;

import org.example.model.OrderEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OrderResultSetMapper {
    OrderEntity map(ResultSet resultSet) throws SQLException;
}
