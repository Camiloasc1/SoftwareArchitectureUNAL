package unal.architecture.test;

import org.junit.*;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class UserServiceIT {
    private static final String URI = "http://localhost:8080/SoftwareArchitectureUNAL/user";
    private static Client client;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        client = ClientBuilder.newClient();
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
        String result = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        assertEquals(result, "Hello!");
    }
}
