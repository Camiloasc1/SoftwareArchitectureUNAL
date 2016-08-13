package unal.architecture.test;

import org.junit.*;
import unal.architecture.entity.User;
import unal.architecture.rest.UserService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/UserService";
    private static UserService service;
    private static Client client;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof UserService);
        service = (UserService) lookup;

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
    public void testAdmin() {
        service.createAdmin();
        User user = service.findByUsername("admin");
        assertNotNull(user);
    }

    @Test
    public void testLogin() {
//        String result = client.target("http://localhost:4204")
//                .request(MediaType.APPLICATION_JSON)
//                .get(String.class);

    }
}
