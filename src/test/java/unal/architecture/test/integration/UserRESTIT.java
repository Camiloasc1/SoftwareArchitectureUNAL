package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.UserCredentials;
import unal.architecture.rest.schemas.Credentials;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

public class UserRESTIT {
    private static final String URI = "http://localhost:8080/SoftwareArchitectureUNAL/users";
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
    public void findAdminFromList() {
        List<UserCredentials> users;

        users = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<UserCredentials>>() {
                });
        assertNotNull(users);
        assertTrue(!users.isEmpty());

        boolean found = false;
        for (UserCredentials u : users) {
            if (u.getUsername().equals("admin")) {
                assertNull(u.getPassword());
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void crudUser() {
        Response response;
        UserCredentials user;

        //Create

        user = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json("{\"username\":\"testuser\",\"password\":\"testuser\",\"roles\":[\"GUEST\"]}"), UserCredentials.class);
        assertNotNull(user);

        //Read
        user = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .get(UserCredentials.class);
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertNull(user.getPassword());

        //Update
        //user.addRole(UserCredentials.Roles.ADMIN);
        user = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json("{\"username\":\"testuser\",\"password\":\"testuser\",\"roles\":[\"ADMIN\"]}"), UserCredentials.class);
        assertNotNull(user);
        assertTrue(user.getRoles().contains(UserCredentials.Roles.ADMIN));

        //Delete
        response = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(204, response.getStatus());

        user = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .get(UserCredentials.class);
        assertNull(user);
    }
}