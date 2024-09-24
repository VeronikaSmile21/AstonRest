package org.example.servlet.mapper;

import org.example.model.OrderEntity;
import org.example.servlet.dto.OrderIncomingDto;
import org.example.servlet.dto.OrderOutGoingDto;

public interface OrderDtomapper {
    OrderEntity map(OrderIncomingDto incomingDto);

    OrderOutGoingDto map(OrderEntity simpleEntity);
}
