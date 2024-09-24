package org.example.servlet.mapper;

import org.example.model.ClientEntity;
import org.example.servlet.dto.ClientIncomingDto;
import org.example.servlet.dto.ClientOutGoingDto;

public interface ClientDtomapper {
    ClientEntity map(ClientIncomingDto incomingDto);

    ClientOutGoingDto map(ClientEntity simpleEntity);
}
