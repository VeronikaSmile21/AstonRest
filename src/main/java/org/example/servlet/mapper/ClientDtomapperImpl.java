package org.example.servlet.mapper;

import org.example.model.ClientEntity;
import org.example.servlet.dto.ClientIncomingDto;
import org.example.servlet.dto.ClientOutGoingDto;

import java.util.stream.Collectors;


public class ClientDtomapperImpl implements ClientDtomapper {

    OrderDtomapper orderDtomapper = OrderDtomapperImpl.getInstance();

    private static ClientDtomapper instance = new ClientDtomapperImpl();

    public static ClientDtomapper getInstance() {
        return instance;
    }

    @Override
    public ClientEntity map(ClientIncomingDto incomingDto) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(MapperUtil.parseInteger(incomingDto.getId()));
        clientEntity.setName(incomingDto.getName());
        clientEntity.setPhone(incomingDto.getPhone());
        return clientEntity;
    }

    @Override
    public ClientOutGoingDto map(ClientEntity clientEntity) {
        ClientOutGoingDto clientOutGoingDto = new ClientOutGoingDto();
        clientOutGoingDto.setId(clientEntity.getId());
        clientOutGoingDto.setName(clientEntity.getName());
        clientOutGoingDto.setPhone(clientEntity.getPhone());
        if(clientEntity.getOrders() != null && clientEntity.getOrders().size() > 0) {
            clientOutGoingDto.setOrders(clientEntity.getOrders().stream().map(o ->
                    orderDtomapper.map(o)).collect(Collectors.toList()));
        }
        return clientOutGoingDto;
    }


}
