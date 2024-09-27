package servlet;

import com.google.gson.Gson;
import org.example.model.AnimalEntity;
import org.example.model.ClientEntity;
import org.example.model.OrderEntity;
import org.example.model.ServiceEntity;
import org.example.service.OrderService;
import org.example.servlet.OrderServlet;
import org.example.servlet.dto.OrderOutGoingDto;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestOrderServlet {

    public static class MyOrderServlet extends OrderServlet {
        private OrderService mockOrderService;

        @Override
        protected OrderService initOrderService() {
            if (mockOrderService == null) {
                mockOrderService = Mockito.mock(OrderService.class);
            }
            return mockOrderService;
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

    static OrderService orderService;
    static MyOrderServlet orderServlet;
    static HttpServletRequest request;
    static HttpServletResponse response;
    static StringWriter stringWriter;

    @BeforeAll
    public static void init() throws IOException {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName("Alex");
        clientEntity.setId(1);
        clientEntity.setPhone("2225568");

        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName("Cat");
        animalEntity.setId(1);
        animalEntity.setPriceCoeff(2);

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Vaccination");
        serviceEntity.setId(1);
        serviceEntity.setPrice(300);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setDate(Date.valueOf("2024-05-19"));
        orderEntity.setStatus(2);
        orderEntity.setAnimal(animalEntity);
        orderEntity.setClient(clientEntity);
        orderEntity.setService(serviceEntity);

        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(2);
        orderEntity2.setDate(Date.valueOf("2024-05-10"));
        orderEntity2.setStatus(2);
        orderEntity2.setAnimal(animalEntity);
        orderEntity2.setClient(clientEntity);
        orderEntity2.setService(serviceEntity);


        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(orderEntity);
        orderEntities.add(orderEntity2);

        orderServlet = new MyOrderServlet();
        orderService = orderServlet.initOrderService();
        when(orderService.findAll()).thenReturn(orderEntities);
        Mockito.doReturn(orderEntity).when(orderService).findById(1);
        Mockito.doReturn(orderEntity2).when(orderService).save(Mockito.any());
        Mockito.doReturn(true).when(orderService).deleteById(Mockito.any());
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
        orderServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        OrderOutGoingDto[] parsedResult = gson.fromJson(result, OrderOutGoingDto[].class);
        Assertions.assertEquals(2, parsedResult.length);
    }

    @Test
    public void testDoGetFindById() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        orderServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        OrderOutGoingDto parsedResult = gson.fromJson(result, OrderOutGoingDto.class);
        Assertions.assertEquals(1, parsedResult.getId());
        Assertions.assertEquals("2024-05-19", parsedResult.getDate());
        Assertions.assertEquals(2, parsedResult.getStatus());
        //OneToMany
        Assertions.assertNotNull(parsedResult.getService());
        Assertions.assertNotNull(parsedResult.getAnimal());
        Assertions.assertNotNull(parsedResult.getClient());
    }

    @Test
    public void testDoPostInsert() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("date")).thenReturn("2024-05-10");
        when(request.getParameter("status")).thenReturn("2");
        when(request.getParameter("client_id")).thenReturn("1");
        when(request.getParameter("animal_id")).thenReturn("1");
        when(request.getParameter("service_id")).thenReturn("1");
        orderServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        OrderOutGoingDto parsedResult = gson.fromJson(result, OrderOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("2024-05-10", parsedResult.getDate());
        Assertions.assertEquals(2, parsedResult.getStatus());
    }

    @Test
    public void testDoPostUpdate() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("date")).thenReturn("2024-05-10");
        when(request.getParameter("status")).thenReturn("2");
        when(request.getParameter("client_id")).thenReturn("1");
        when(request.getParameter("animal_id")).thenReturn("1");
        when(request.getParameter("service_id")).thenReturn("1");
        orderServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        OrderOutGoingDto parsedResult = gson.fromJson(result, OrderOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("2024-05-10", parsedResult.getDate());
        Assertions.assertEquals(2, parsedResult.getStatus());
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        orderServlet.doDelete(request, response);
        String result = stringWriter.toString();
        Assertions.assertEquals("true", result);
    }
}

