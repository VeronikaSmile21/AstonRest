package org.example.servlet.mapper;

import org.example.model.AnimalEntity;
import org.example.model.ClientEntity;
import org.example.model.OrderEntity;
import org.example.model.ServiceEntity;
import org.example.servlet.dto.OrderIncomingDto;
import org.example.servlet.dto.OrderOutGoingDto;


public class OrderDtomapperImpl implements OrderDtomapper {
    AnimalDtomapper animalDtomapper = new AnimalDtomapperImpl();
    ClientDtomapper clientDtomapper = new ClientDtomapperImpl();
    ServiceDtomapper serviceDtomapper = new ServiceDtomapperImpl();

    @Override
    public OrderEntity map(OrderIncomingDto incomingDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(MapperUtil.parseInteger(incomingDto.getId()));

        ClientEntity clientEntity = new ClientEntity();
        orderEntity.setClient(clientEntity);

        ServiceEntity serviceEntity = new ServiceEntity();
        orderEntity.setService(serviceEntity);

        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(MapperUtil.parseInteger(incomingDto.getAnimalId()));
        orderEntity.setAnimal(animalEntity);
        orderEntity.setDate(MapperUtil.parseDate(incomingDto.getDate()));
        orderEntity.setStatus(MapperUtil.parseInteger(incomingDto.getStatus()));
        orderEntity.setCost(MapperUtil.parseInteger(incomingDto.getCost()));
        return orderEntity;
    }

    @Override
    public OrderOutGoingDto map(OrderEntity orderEntity) {
        OrderOutGoingDto orderOutGoingDto = new OrderOutGoingDto();
        orderOutGoingDto.setId(orderEntity.getId());
        orderOutGoingDto.setClient(clientDtomapper.map(orderEntity.getClient()));
        orderOutGoingDto.setService(serviceDtomapper.map(orderEntity.getService()));
        orderOutGoingDto.setAnimal(animalDtomapper.map(orderEntity.getAnimal()));
        orderOutGoingDto.setDate(String.valueOf(orderEntity.getDate()));
        orderOutGoingDto.setStatus(orderEntity.getStatus());
        orderOutGoingDto.setCost(orderEntity.getCost());
        return orderOutGoingDto;
    }


}
