package org.example.servlet.mapper;

import org.example.model.AnimalEntity;
import org.example.servlet.dto.AnimalIncomingDto;
import org.example.servlet.dto.AnimalOutGoingDto;


public class AnimalDtomapperImpl implements AnimalDtomapper {

    private static AnimalDtomapper instance = new AnimalDtomapperImpl();

    public static AnimalDtomapper getInstance() {
        return instance;
    }

    @Override
    public AnimalEntity map(AnimalIncomingDto incomingDto) {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(MapperUtil.parseInteger(incomingDto.getId()));
        animalEntity.setName(incomingDto.getName());
        animalEntity.setPriceCoeff(MapperUtil.parseFloat(incomingDto.getPriceCoeff()));
        return animalEntity;
    }

    @Override
    public AnimalOutGoingDto map(AnimalEntity animalEntity) {
        AnimalOutGoingDto animalOutGoingDto = new AnimalOutGoingDto();
        animalOutGoingDto.setId(animalEntity.getId());
        animalOutGoingDto.setName(animalEntity.getName());
        animalOutGoingDto.setPriceCoeff(animalEntity.getPriceCoeff());
        animalOutGoingDto.setServices(animalEntity.getServices().stream().map(s ->
                ServiceDtomapperImpl.getInstance().map(s)).toList());
        return animalOutGoingDto;
    }


}
