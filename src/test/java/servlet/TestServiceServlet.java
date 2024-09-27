package servlet;

import com.google.gson.Gson;
import org.example.model.AnimalEntity;
import org.example.model.ServiceEntity;
import org.example.service.ServiceService;
import org.example.servlet.ServiceServlet;
import org.example.servlet.dto.ServiceOutGoingDto;
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
public class TestServiceServlet {

    public static class MyServiceServlet extends ServiceServlet {
        private ServiceService mockServiceService;

        @Override
        protected ServiceService initServiceService() {
            if (mockServiceService == null) {
                mockServiceService = Mockito.mock(ServiceService.class);
            }
            return mockServiceService;
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

    static ServiceService serviceService;
    static MyServiceServlet serviceServlet;
    static HttpServletRequest request;
    static HttpServletResponse response;
    static StringWriter stringWriter;

    @BeforeAll
    public static void init() throws IOException {

        AnimalEntity animalEntity = new AnimalEntity();
        AnimalEntity animalEntity1 = new AnimalEntity();

        List<AnimalEntity> animalEntities = new ArrayList<>();
        animalEntities.add(animalEntity);
        animalEntities.add(animalEntity1);

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Vaccination");
        serviceEntity.setId(1);
        serviceEntity.setPrice(300);
        serviceEntity.setAnimals(animalEntities);

        ServiceEntity serviceEntity2 = new ServiceEntity();
        serviceEntity2.setName("Chipping");
        serviceEntity2.setId(2);
        serviceEntity2.setPrice(200);

        List<ServiceEntity> serviceEntities = new ArrayList<>();
        serviceEntities.add(serviceEntity);
        serviceEntities.add(serviceEntity2);

        serviceServlet = new MyServiceServlet();
        serviceService = serviceServlet.initServiceService();
        when(serviceService.findAll()).thenReturn(serviceEntities);
        Mockito.doReturn(serviceEntity).when(serviceService).findById(1);
        Mockito.doReturn(serviceEntity2).when(serviceService).save(Mockito.any());
        Mockito.doReturn(true).when(serviceService).deleteById(Mockito.any());

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
        serviceServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ServiceOutGoingDto[] parsedResult = gson.fromJson(result, ServiceOutGoingDto[].class);
        Assertions.assertEquals(2, parsedResult.length);
    }

    @Test
    public void testDoGetFindById() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        serviceServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ServiceOutGoingDto parsedResult = gson.fromJson(result, ServiceOutGoingDto.class);
        Assertions.assertEquals(1, parsedResult.getId());
        Assertions.assertEquals("Vaccination", parsedResult.getName());
        Assertions.assertEquals(300, parsedResult.getPrice());
        //ManyToMany
        Assertions.assertEquals(2, parsedResult.getAnimals().size());
    }

    @Test
    public void testDoPostInsert() throws ServletException, IOException {
        Mockito.doReturn(null).when(request).getParameter("id");
        Mockito.doReturn("Chipping").when(request).getParameter("name");
        Mockito.doReturn("200").when(request).getParameter("price");
        serviceServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ServiceOutGoingDto parsedResult = gson.fromJson(result, ServiceOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("Chipping", parsedResult.getName());
        Assertions.assertEquals(200, parsedResult.getPrice());
    }

    @Test
    public void testDoPostUpdate() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("name")).thenReturn("Chipping");
        when(request.getParameter("price")).thenReturn("200");
        serviceServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ServiceOutGoingDto parsedResult = gson.fromJson(result, ServiceOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("Chipping", parsedResult.getName());
        Assertions.assertEquals(200, parsedResult.getPrice());
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        serviceServlet.doDelete(request, response);
        String result = stringWriter.toString();
        Assertions.assertEquals("true", result);
    }
}

