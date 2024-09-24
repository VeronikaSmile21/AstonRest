package org.example.servlet;

import com.google.gson.Gson;
import org.example.model.AnimalEntity;
import org.example.service.AnimalService;
import org.example.service.impl.AnimalServiceImpl;
import org.example.servlet.dto.AnimalIncomingDto;
import org.example.servlet.dto.AnimalOutGoingDto;
import org.example.servlet.mapper.AnimalDtomapper;
import org.example.servlet.mapper.AnimalDtomapperImpl;
import org.example.servlet.mapper.MapperUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@WebServlet("/animal")
public class AnimalServlet extends HttpServlet {
    private AnimalService animalService = new AnimalServiceImpl();
    private AnimalDtomapper animalDtomapper = new AnimalDtomapperImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            AnimalEntity animalEntity = animalService.findById(MapperUtil.parseInteger(idParam));
            if(animalEntity != null) {
                AnimalOutGoingDto animalOutGoingDto = animalDtomapper.map(animalEntity);
                gson.toJson(animalOutGoingDto, printWriter);
            }
        } else {
            List<AnimalEntity> animalEntities = animalService.findAll();
            gson.toJson(animalEntities, printWriter);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        AnimalIncomingDto animalIncomingDto = new AnimalIncomingDto();

        animalIncomingDto.setId(req.getParameter("id"));
        animalIncomingDto.setName(req.getParameter("name"));
        animalIncomingDto.setPriceCoeff(req.getParameter("priceCoeff"));

        AnimalEntity animalEntity = animalDtomapper.map(animalIncomingDto);
        AnimalEntity saved = animalService.save(animalEntity);
        AnimalOutGoingDto map = animalDtomapper.map(saved);
        gson.toJson(map, printWriter);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            boolean result = animalService.deleteById(MapperUtil.parseInteger(idParam));
            gson.toJson(result, printWriter);
        }
        printWriter.close();
    }



}
