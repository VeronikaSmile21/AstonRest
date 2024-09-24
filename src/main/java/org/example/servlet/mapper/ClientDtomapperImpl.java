package org.example.servlet.mapper;

import org.example.model.ClientEntity;
import org.example.servlet.dto.ClientIncomingDto;
import org.example.servlet.dto.ClientOutGoingDto;


public class ClientDtomapperImpl implements ClientDtomapper {
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
        return clientOutGoingDto;
    }


}
