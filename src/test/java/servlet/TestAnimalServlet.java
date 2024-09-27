package servlet;

import com.google.gson.Gson;
import org.example.model.AnimalEntity;
import org.example.model.ServiceEntity;
import org.example.service.AnimalService;
import org.example.servlet.AnimalServlet;
import org.example.servlet.dto.AnimalOutGoingDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestAnimalServlet {

    public static class MyAnimalServlet extends AnimalServlet {
        private AnimalService mockAnimalService;

        @Override
        protected AnimalService initAnimalService() {
            if (mockAnimalService == null) {
                mockAnimalService = Mockito.mock(AnimalService.class);
            }
            return mockAnimalService;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doGet(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doPost(req, resp);
        }

        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doDelete(req, resp);
        }
    }

    static AnimalService animalService;
    static MyAnimalServlet animalServlet;
    static HttpServletRequest request;
    static HttpServletResponse response;
    static StringWriter stringWriter;

    @BeforeAll
    public static void init() throws IOException {

        ServiceEntity serviceEntity = new ServiceEntity();
        ServiceEntity serviceEntity1 = new ServiceEntity();

        List<ServiceEntity> serviceEntities = new ArrayList<>();
        serviceEntities.add(serviceEntity);
        serviceEntities.add(serviceEntity1);

        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName("Cat");
        animalEntity.setId(1);
        animalEntity.setPriceCoeff(2);
        animalEntity.setServices(serviceEntities);

        AnimalEntity animalEntity2 = new AnimalEntity();
        animalEntity2.setName("Dog");
        animalEntity2.setId(2);
        animalEntity2.setPriceCoeff(3);

        List<AnimalEntity> animalEntities = new ArrayList<>();
        animalEntities.add(animalEntity);
        animalEntities.add(animalEntity2);

        animalServlet = new MyAnimalServlet();
        animalService = animalServlet.initAnimalService();
        when(animalService.findAll()).thenReturn(animalEntities);
        Mockito.doReturn(animalEntity).when(animalService).findById(1);
        Mockito.doReturn(animalEntity2).when(animalService).save(Mockito.any());
        Mockito.doReturn(true).when(animalService).deleteById(Mockito.any());
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

    }

    @Test
    public void testDoGetFindAll() throws ServletException, IOException {
        animalServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        AnimalOutGoingDto[] parsedResult = gson.fromJson(result, AnimalOutGoingDto[].class);
        Assertions.assertEquals(2, parsedResult.length);
    }

    @Test
    public void testDoGetFindById() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        animalServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        AnimalOutGoingDto parsedResult = gson.fromJson(result, AnimalOutGoingDto.class);
        Assertions.assertEquals(1, parsedResult.getId());
        Assertions.assertEquals("Cat", parsedResult.getName());
        Assertions.assertEquals(2, parsedResult.getPriceCoeff());
        //ManyToMany
        Assertions.assertEquals(2,parsedResult.getServices().size());
    }

    @Test
    public void testDoPostInsert() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("name")).thenReturn("Dog");
        when(request.getParameter("priceCoeff")).thenReturn("3");
        animalServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        AnimalOutGoingDto parsedResult = gson.fromJson(result, AnimalOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("Dog", parsedResult.getName());
        Assertions.assertEquals(3, parsedResult.getPriceCoeff());
    }

    @Test
    public void testDoPostUpdate() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("name")).thenReturn("Dog");
        when(request.getParameter("priceCoeff")).thenReturn("3");
        animalServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        AnimalOutGoingDto parsedResult = gson.fromJson(result, AnimalOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("Dog", parsedResult.getName());
        Assertions.assertEquals(3, parsedResult.getPriceCoeff());
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        animalServlet.doDelete(request, response);
        String result = stringWriter.toString();
        Assertions.assertEquals("true", result);
    }
}

