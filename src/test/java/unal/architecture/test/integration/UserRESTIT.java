package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.User;

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
        List<User> users;

        users = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<User>>() {
                });
        assertNotNull(users);
        assertTrue(!users.isEmpty());

        boolean found = false;
        for (User u : users) {
            if (u.getUsername().equals("admin")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void findAdmin() {
        User user;

        user = client.target(URI)
                .path("admin")
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        assertNotNull(user);
    }

    @Test
    public void crudUser() {
        Response response;
        User user;

        //Create
        user = new User();
        user.setName("Test User");
        user.setUsername("testuser");

        user = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(user), User.class);
        assertNotNull(user);

        //Read
        user = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals("testuser", user.getUsername());

        //Update
        user.setEmail("testuser@architecure.unal");
        user = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(user), User.class);
        assertNotNull(user);
        assertEquals("testuser@architecure.unal", user.getEmail());

        //Delete
        response = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(204, response.getStatus());

        user = client.target(URI)
                .path(user.getUsername())
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        assertNull(user);
    }
}