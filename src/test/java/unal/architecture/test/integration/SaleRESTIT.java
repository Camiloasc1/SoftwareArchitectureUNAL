package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Sale;
import unal.architecture.rest.schemas.Credentials;
import unal.architecture.test.util.ITUtil;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class SaleRESTIT {
    private static final String PATH = "sales";
    private static final String URI = ITUtil.BASE_URI + PATH;
    private static Client client;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    }

    @AfterClass
    public static void afterClass() throws NamingException {
        client.close();
    }

    private String session;

    @Before
    public void before() {
        Credentials credentials = new Credentials();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        Response response = client.target("http://localhost:8080/SoftwareArchitectureUNAL/auth")
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(credentials));
        session = response.getCookies().get("JSESSIONID").getValue();
    }

    @After
    public void after() {
    }

    @Test
    public void crudProduct() {
        Response response;
        Sale sale;

        String session = ITUtil.getAdminSession();

        //Create
        sale = new Sale();
        sale.setClient("Test Client");

        sale = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(sale), Sale.class);
        assertNotNull(sale);

        //Read
        sale = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Sale.class);
        assertNotNull(sale);
        assertEquals("Test Client", sale.getClient());

        //Update
        sale.setClient("Test Client 2");
        sale = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .put(Entity.json(sale), Sale.class);
        assertNotNull(sale);
        assertEquals("Test Client 2", sale.getClient());

        //Delete
        response = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .delete();
        assertEquals(204, response.getStatus());

        sale = client.target(URI)
                .path(sale.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Sale.class);
        assertNull(sale);
    }
}