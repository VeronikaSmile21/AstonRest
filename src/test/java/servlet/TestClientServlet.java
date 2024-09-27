package servlet;

import com.google.gson.Gson;
import org.example.model.AnimalEntity;
import org.example.model.ClientEntity;
import org.example.model.OrderEntity;
import org.example.model.ServiceEntity;
import org.example.service.ClientService;
import org.example.servlet.ClientServlet;
import org.example.servlet.dto.ClientOutGoingDto;
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
public class TestClientServlet {

    public static class MyClientServlet extends ClientServlet {
        private ClientService mockClientService;

        @Override
        protected ClientService initClientService() {
            if (mockClientService == null) {
                mockClientService = Mockito.mock(ClientService.class);
            }
            return mockClientService;
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

    static ClientService clientService;
    static MyClientServlet clientServlet;
    static HttpServletRequest request;
    static HttpServletResponse response;
    static StringWriter stringWriter;

    @BeforeAll
    public static void init() throws IOException {

        OrderEntity orderEntity = new OrderEntity();
        OrderEntity orderEntity1 = new OrderEntity();

        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(orderEntity);
        orderEntities.add(orderEntity1);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName("Alex");
        clientEntity.setId(1);
        clientEntity.setPhone("2225568");
        clientEntity.setOrders(orderEntities);

        ClientEntity clientEntityCopy = new ClientEntity();
        clientEntityCopy.setId(clientEntity.getId());
        clientEntityCopy.setName(clientEntity.getName());
        clientEntityCopy.setPhone(clientEntity.getPhone());
        orderEntity.setClient(clientEntityCopy);
        orderEntity1.setClient(clientEntityCopy);

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Vaccination");
        serviceEntity.setId(1);
        serviceEntity.setPrice(300);

        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName("Cat");
        animalEntity.setId(1);
        animalEntity.setPriceCoeff(2);

        orderEntity.setService(serviceEntity);
        orderEntity.setAnimal(animalEntity);
        orderEntity1.setService(serviceEntity);
        orderEntity1.setAnimal(animalEntity);

        ClientEntity clientEntity2 = new ClientEntity();
        clientEntity2.setName("Jessica");
        clientEntity2.setId(2);
        clientEntity2.setPhone("333335557");


        List<ClientEntity> clientEntities = new ArrayList<>();
        clientEntities.add(clientEntity);
        clientEntities.add(clientEntity2);

        clientServlet = new MyClientServlet();
        clientService = clientServlet.initClientService();
        when(clientService.findAll()).thenReturn(clientEntities);
        Mockito.doReturn(clientEntity).when(clientService).findById(1);
        Mockito.doReturn(clientEntity2).when(clientService).save(Mockito.any());
        Mockito.doReturn(true).when(clientService).deleteById(Mockito.any());
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
        clientServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ClientOutGoingDto[] parsedResult = gson.fromJson(result, ClientOutGoingDto[].class);
        Assertions.assertEquals(2, parsedResult.length);
    }

    @Test
    public void testDoGetFindById() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        clientServlet.doGet(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ClientOutGoingDto parsedResult = gson.fromJson(result, ClientOutGoingDto.class);
        Assertions.assertEquals(1, parsedResult.getId());
        Assertions.assertEquals("Alex", parsedResult.getName());
        Assertions.assertEquals("2225568", parsedResult.getPhone());
        //ManyToMany
        Assertions.assertEquals(2,parsedResult.getOrders().size());
    }

    @Test
    public void testDoPostInsert() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("name")).thenReturn("Jessica");
        when(request.getParameter("phone")).thenReturn("333335557");
        clientServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ClientOutGoingDto parsedResult = gson.fromJson(result, ClientOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("Jessica", parsedResult.getName());
        Assertions.assertEquals("333335557", parsedResult.getPhone());
    }

    @Test
    public void testDoPostUpdate() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("name")).thenReturn("Jessica");
        when(request.getParameter("phone")).thenReturn("333335557");
        clientServlet.doPost(request, response);
        String result = stringWriter.toString();
        Gson gson = new Gson();
        ClientOutGoingDto parsedResult = gson.fromJson(result, ClientOutGoingDto.class);
        Assertions.assertEquals(2, parsedResult.getId());
        Assertions.assertEquals("Jessica", parsedResult.getName());
        Assertions.assertEquals("333335557", parsedResult.getPhone());
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        clientServlet.doDelete(request, response);
        String result = stringWriter.toString();
        Assertions.assertEquals("true", result);
    }
}

