package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Product;
import unal.architecture.entity.Sale;
import unal.architecture.entity.User;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Date;

import static org.junit.Assert.*;

public class SaleRESTIT {
    private static final String URI = "http://localhost:8080/SoftwareArchitectureUNAL/products";
    private static Client client;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    }

    @AfterClass
    public static void afterClass() throws NamingException {
        client.close();
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    @Ignore
    public void crudProduct() {
        Response response;
        Sale sale;

        User seller = client.target("http://localhost:8080/SoftwareArchitectureUNAL/users")
                .path("admin")
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        assertNotNull(seller);

        //Create
        sale = new Sale();
        sale.setClient("Test Client");
        sale.setSeller(seller);

        sale = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(sale), Sale.class);
        assertNotNull(sale);

        //Read
        sale = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Sale.class);
        assertNotNull(sale);
        assertEquals("Test Client", sale.getClient());
        assertEquals(seller, sale.getSeller());

        //Update
        sale.setClient("Test Client 2");
        sale = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(sale), Sale.class);
        assertNotNull(sale);
        assertEquals("Test Client 2", sale.getClient());

        //Delete
        response = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(204, response.getStatus());

        sale = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Sale.class);
        assertNull(sale);
    }
}