package org.example.repository.mapper;

import org.example.model.AnimalEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalResultSetMapperImpl implements AnimalResultSetMapper {
    @Override
    public AnimalEntity map(ResultSet resultSet) throws SQLException {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName(resultSet.getString("name"));
        animalEntity.setId(resultSet.getInt("id"));
        animalEntity.setPriceCoeff(resultSet.getFloat("price_coeff"));
        return animalEntity;
    }
}
