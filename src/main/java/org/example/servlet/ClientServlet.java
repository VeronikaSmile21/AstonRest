package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.ClientEntity;
import org.example.service.ClientService;
import org.example.service.impl.ClientServiceImpl;
import org.example.servlet.dto.ClientIncomingDto;
import org.example.servlet.dto.ClientOutGoingDto;
import org.example.servlet.mapper.MapperUtil;
import org.example.servlet.mapper.ClientDtomapper;
import org.example.servlet.mapper.ClientDtomapperImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private ClientService clientService = new ClientServiceImpl();
    private ClientDtomapper clientDtomapper = new ClientDtomapperImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            ClientEntity clientEntity = clientService.findById(MapperUtil.parseInteger(idParam));
            if(clientEntity != null) {
                ClientOutGoingDto clientOutGoingDto = clientDtomapper.map(clientEntity);
                gson.toJson(clientOutGoingDto, printWriter);
            }
        } else {
            List<ClientEntity> clientEntities = clientService.findAll();
            gson.toJson(clientEntities, printWriter);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        ClientIncomingDto clientIncomingDto = new ClientIncomingDto();

        clientIncomingDto.setId(req.getParameter("id"));
        clientIncomingDto.setName(req.getParameter("name"));
        clientIncomingDto.setPhone(req.getParameter("phone"));

        ClientEntity clientEntity = clientDtomapper.map(clientIncomingDto);
        ClientEntity saved = clientService.save(clientEntity);
        ClientOutGoingDto map = clientDtomapper.map(saved);
        gson.toJson(map, printWriter);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            boolean result = clientService.deleteById(MapperUtil.parseInteger(idParam));
            gson.toJson(result, printWriter);
        }
        printWriter.close();
    }



}
