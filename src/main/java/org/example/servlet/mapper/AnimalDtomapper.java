package org.example.servlet.mapper;

import org.example.model.AnimalEntity;
import org.example.servlet.dto.AnimalIncomingDto;
import org.example.servlet.dto.AnimalOutGoingDto;

public interface AnimalDtomapper {
    AnimalEntity map(AnimalIncomingDto incomingDto);

    AnimalOutGoingDto map(AnimalEntity simpleEntity);
}
