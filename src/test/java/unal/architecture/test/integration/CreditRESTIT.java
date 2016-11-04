package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Credit;
import unal.architecture.test.util.ITUtil;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class CreditRESTIT {
    private static final String PATH = "credits";
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

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void crudCredit() {
        Response response;
        Credit credit;

        String session = ITUtil.getAdminSession();

        //Create
        credit = new Credit();
        credit.setAmount(3.1415f);
        credit.setNumberOfPayments(36);
        credit.setType("Em-Amigable");
        credit.setPaid(false);

        credit = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(credit), Credit.class);
        assertNotNull(credit);

        //Read
        credit = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Credit.class);
        assertNotNull(credit);
        assertEquals(false, credit.isPaid());

        //Update
        credit.setPaid(true);
        credit = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .put(Entity.json(credit), Credit.class);
        assertNotNull(credit);
        assertEquals(true, credit.isPaid());

        //Delete
        response = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .delete();
        assertEquals(204, response.getStatus());

        credit = client.target(URI)
                .path(credit.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Credit.class);
        assertNull(credit);
    }
}