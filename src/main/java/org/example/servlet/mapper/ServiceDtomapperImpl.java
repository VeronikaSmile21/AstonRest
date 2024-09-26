package org.example.servlet.mapper;

import org.example.model.ServiceEntity;
import org.example.servlet.dto.ServiceIncomingDto;
import org.example.servlet.dto.ServiceOutGoingDto;


public class ServiceDtomapperImpl implements ServiceDtomapper {

    private static ServiceDtomapper instance = new ServiceDtomapperImpl();

    public static ServiceDtomapper getInstance() {
        return instance;
    }

    @Override
    public ServiceEntity map(ServiceIncomingDto incomingDto) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(MapperUtil.parseInteger(incomingDto.getId()));
        serviceEntity.setName(incomingDto.getName());
        serviceEntity.setPrice(MapperUtil.parseFloat(incomingDto.getPrice()));
        return serviceEntity;
    }

    @Override
    public ServiceOutGoingDto map(ServiceEntity serviceEntity) {
        ServiceOutGoingDto serviceOutGoingDto = new ServiceOutGoingDto();
        serviceOutGoingDto.setId(serviceEntity.getId());
        serviceOutGoingDto.setName(serviceEntity.getName());
        serviceOutGoingDto.setPrice(serviceEntity.getPrice());
        serviceOutGoingDto.setAnimals(serviceEntity.getAnimals().stream().map(a ->
                AnimalDtomapperImpl.getInstance().map(a)).toList());
        return serviceOutGoingDto;
    }


}
