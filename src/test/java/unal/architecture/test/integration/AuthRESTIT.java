package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import org.junit.rules.ExpectedException;
import unal.architecture.entity.User;
import unal.architecture.rest.schemas.Credentials;
import unal.architecture.rest.schemas.PasswordChange;
import unal.architecture.test.util.ITUtil;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;

public class AuthRESTIT {
    private static final String PATH = "auth";
    private static final String URI = ITUtil.BASE_URI + PATH;
    private static Client client;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void testNotLoggedin() {
        thrown.expect(javax.ws.rs.ForbiddenException.class);
        client.target(URI)
                .path("me")
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
    }

    @Test
    public void testLogin() {
        Response response;
        User user;

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
        thrown.expect(javax.ws.rs.ForbiddenException.class);
        client.target(URI)
                .path("me")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(User.class);
    }

    @Test
    public void testPasswd() {
        Response response;
        User user;
        Credentials credentials;
        String session;
        PasswordChange passwordChange;

        //Login.
        credentials = new Credentials();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        response = client.target(URI)
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(credentials));
        session = response.getCookies().get("JSESSIONID").getValue();
        user = response.readEntity(User.class);
        assertNotNull(user);

        //Change Password.
        passwordChange = new PasswordChange();
        passwordChange.setUsername("admin");
        passwordChange.setOldPassword("admin");
        passwordChange.setNewPassword("nimda");
        client.target(URI)
                .path("passwd")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .put(Entity.json(passwordChange));

        //Logout.
        client.target(URI)
                .path("logout")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(""));

        //Login again.
        credentials = new Credentials();
        credentials.setUsername("admin");
        credentials.setPassword("nimda");
        response = client.target(URI)
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(credentials));
        session = response.getCookies().get("JSESSIONID").getValue();
        user = response.readEntity(User.class);
        assertNotNull(user);

        //Change Password again.
        passwordChange = new PasswordChange();
        passwordChange.setUsername("admin");
        passwordChange.setOldPassword("nimda");
        passwordChange.setNewPassword("admin");
        client.target(URI)
                .path("passwd")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .put(Entity.json(passwordChange));

        //Logout again.
        client.target(URI)
                .path("logout")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(""));

        //Login failed.
        credentials = new Credentials();
        credentials.setUsername("admin");
        credentials.setPassword("nimda");
        thrown.expect(javax.ws.rs.ForbiddenException.class);
        client.target(URI)
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(credentials), User.class);
    }
}