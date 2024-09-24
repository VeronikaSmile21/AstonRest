package org.example.servlet.mapper;

import org.example.model.OrderEntity;
import org.example.servlet.dto.OrderIncomingDto;
import org.example.servlet.dto.OrderOutGoingDto;


public class OrderDtomapperImpl implements OrderDtomapper {
    @Override
    public OrderEntity map(OrderIncomingDto incomingDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(MapperUtil.parseInteger(incomingDto.getId()));
        orderEntity.setClientId(MapperUtil.parseInteger(incomingDto.getClientId()));
        orderEntity.setServiceId(MapperUtil.parseInteger(incomingDto.getServiceId()));
        orderEntity.setAnimalId(MapperUtil.parseInteger(incomingDto.getAnimalId()));
        orderEntity.setDate(MapperUtil.parseDate(incomingDto.getDate()));
        orderEntity.setStatus(MapperUtil.parseInteger(incomingDto.getStatus()));
        orderEntity.setCost(MapperUtil.parseInteger(incomingDto.getCost()));
        return orderEntity;
    }

    @Override
    public OrderOutGoingDto map(OrderEntity orderEntity) {
        OrderOutGoingDto orderOutGoingDto = new OrderOutGoingDto();
        orderOutGoingDto.setId(orderEntity.getId());
        orderOutGoingDto.setClientId(orderEntity.getClientId());
        orderOutGoingDto.setServiceId(orderEntity.getServiceId());
        orderOutGoingDto.setAnimalId(orderEntity.getAnimalId());
        orderOutGoingDto.setDate(String.valueOf(orderEntity.getDate()));
        orderOutGoingDto.setStatus(orderEntity.getStatus());
        orderOutGoingDto.setCost(orderEntity.getCost());
        return orderOutGoingDto;
    }


}
