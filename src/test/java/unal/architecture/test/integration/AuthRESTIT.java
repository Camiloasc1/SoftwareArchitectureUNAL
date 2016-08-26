package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Credentials;
import unal.architecture.entity.User;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AuthRESTIT {
    private static final String URI = "http://localhost:8080/SoftwareArchitectureUNAL/auth";
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
    public void testLogin() {
        Response response;
        User user;

        //Not logged in yet.
        user = client.target(URI)
                .path("me")
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        assertNull(user);

        //Login.
        Credentials credentials = new Credentials();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        response = client.target(URI)
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(credentials));
        String session = response.getCookies().get("JSESSIONID").getValue();
        user = response.readEntity(User.class);
        assertNotNull(user);

        //Logged in.
        user = client.target(URI)
                .path("me")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(User.class);
        assertNotNull(user);

        //Logout.
        client.target(URI)
                .path("logout")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(""));

        //Not logged in again.
        user = client.target(URI)
                .path("me")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(User.class);
        assertNull(user);
    }
}