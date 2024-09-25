package org.example.repository.mapper;

import org.example.model.AnimalEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalResultSetMapperImpl implements AnimalResultSetMapper {
    @Override
    public AnimalEntity map(ResultSet resultSet) throws SQLException {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName(resultSet.getString("a_name"));
        animalEntity.setId(resultSet.getInt("a_id"));
        animalEntity.setPriceCoeff(resultSet.getFloat("a_price_coeff"));
        return animalEntity;
    }
}
