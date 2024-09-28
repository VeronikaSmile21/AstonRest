package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.ServiceEntity;
import org.example.service.ServiceService;
import org.example.service.impl.ServiceServiceImpl;
import org.example.servlet.dto.ServiceIncomingDto;
import org.example.servlet.dto.ServiceOutGoingDto;
import org.example.servlet.mapper.MapperUtil;
import org.example.servlet.mapper.ServiceDtomapper;
import org.example.servlet.mapper.ServiceDtomapperImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/service")
public class ServiceServlet extends HttpServlet {
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    private ServiceService serviceService = initServiceService();
    private ServiceDtomapper serviceDtomapper = new ServiceDtomapperImpl();
    private Gson gson = new Gson();

    protected ServiceService initServiceService() {
        return new ServiceServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            ServiceEntity serviceEntity = serviceService.findById(MapperUtil.parseInteger(idParam));
            if(serviceEntity != null) {
                ServiceOutGoingDto serviceOutGoingDto = serviceDtomapper.map(serviceEntity);
                gson.toJson(serviceOutGoingDto, printWriter);
            }
        } else {
            List<ServiceEntity> serviceEntities = serviceService.findAll();
            gson.toJson(serviceEntities, printWriter);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        PrintWriter printWriter = resp.getWriter();

        ServiceIncomingDto serviceIncomingDto = new ServiceIncomingDto();

        serviceIncomingDto.setId(req.getParameter("id"));
        serviceIncomingDto.setName(req.getParameter("name"));
        serviceIncomingDto.setPrice(req.getParameter("price"));

        ServiceEntity serviceEntity = serviceDtomapper.map(serviceIncomingDto);
        ServiceEntity saved = serviceService.save(serviceEntity);
        ServiceOutGoingDto map = serviceDtomapper.map(saved);
        gson.toJson(map, printWriter);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            boolean result = serviceService.deleteById(MapperUtil.parseInteger(idParam));
            gson.toJson(result, printWriter);
        }
        printWriter.close();
    }



}
