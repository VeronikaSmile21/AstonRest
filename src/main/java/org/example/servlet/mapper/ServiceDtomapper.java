package org.example.servlet.mapper;

import org.example.model.ServiceEntity;
import org.example.servlet.dto.ServiceIncomingDto;
import org.example.servlet.dto.ServiceOutGoingDto;

public interface ServiceDtomapper {
    ServiceEntity map(ServiceIncomingDto incomingDto);

    ServiceOutGoingDto map(ServiceEntity simpleEntity);
}
