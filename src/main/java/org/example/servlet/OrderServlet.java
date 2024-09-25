package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.OrderEntity;
import org.example.service.OrderService;
import org.example.service.impl.OrderServiceImpl;
import org.example.servlet.dto.OrderIncomingDto;
import org.example.servlet.dto.OrderOutGoingDto;
import org.example.servlet.mapper.OrderDtomapper;
import org.example.servlet.mapper.OrderDtomapperImpl;
import org.example.servlet.mapper.MapperUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private OrderService orderService = new OrderServiceImpl();
    private OrderDtomapper orderDtomapper = new OrderDtomapperImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            OrderEntity orderEntity = orderService.findById(MapperUtil.parseInteger(idParam));
            if(orderEntity != null) {
                OrderOutGoingDto orderOutGoingDto = orderDtomapper.map(orderEntity);
                gson.toJson(orderOutGoingDto, printWriter);
            }
        } else {
            List<OrderEntity> orderEntities = orderService.findAll();
            List<OrderOutGoingDto> result = orderEntities.stream().map(o ->
                    orderDtomapper.map(o)).collect(Collectors.toList());
            gson.toJson(result, printWriter);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        OrderIncomingDto orderIncomingDto = new OrderIncomingDto();

        orderIncomingDto.setId(req.getParameter("id"));
        orderIncomingDto.setClientId(req.getParameter("client_id"));
        orderIncomingDto.setServiceId(req.getParameter("service_id"));
        orderIncomingDto.setAnimalId(req.getParameter("animal_id"));
        orderIncomingDto.setDate(req.getParameter("date"));
        orderIncomingDto.setStatus(req.getParameter("status"));
        orderIncomingDto.setCost(req.getParameter("cost"));

        OrderEntity orderEntity = orderDtomapper.map(orderIncomingDto);
        OrderEntity saved = orderService.save(orderEntity);
        OrderOutGoingDto map = orderDtomapper.map(saved);
        gson.toJson(map, printWriter);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            boolean result = orderService.deleteById(MapperUtil.parseInteger(idParam));
            gson.toJson(result, printWriter);
        }
        printWriter.close();
    }



}
