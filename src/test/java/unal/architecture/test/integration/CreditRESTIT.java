package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Credit;
import unal.architecture.entity.User;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class CreditRESTIT {
    private static final String URI = "http://localhost:8080/SoftwareArchitectureUNAL/credits";
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
    public void crudProduct() {
        Response response;
        Credit credit;

        User admin = new User();
        admin.setName("Test User");
        admin.setAdmin(true);
        admin = client.target("http://localhost:8080/SoftwareArchitectureUNAL/users")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(admin), User.class);
        assertNotNull(admin);

        //Create
        credit = new Credit();
        credit.setInterest(0.1f);
        credit.setPaid(false);
        credit.setUser(admin);

        credit = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(credit), Credit.class);
        assertNotNull(credit);

        //Read
        credit = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Credit.class);
        assertNotNull(credit);
        assertEquals(0.1f, credit.getInterest(), 0.01f);
        assertEquals(false, credit.isPaid());

        //Update
        credit.setPaid(true);
        credit = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(credit), Credit.class);
        assertNotNull(credit);
        assertEquals(true, credit.isPaid());

        //Delete
        response = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(204, response.getStatus());

        credit = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Credit.class);
        assertNull(credit);
    }
}