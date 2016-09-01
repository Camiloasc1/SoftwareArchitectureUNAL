package unal.architecture.test.util;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import unal.architecture.rest.schemas.Credentials;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ITUtil {
    public static final String BASE_URI = "http://localhost:8080/SoftwareArchitectureUNAL/";
    private static Client client;
    private static String session;

    static {
        client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    }

    public static String getAdminSession() {
        if (session == null) {
            Credentials credentials = new Credentials();
            credentials.setUsername("admin");
            credentials.setPassword("admin");
            Response response = client.target(BASE_URI + "auth")
                    .path("login")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(credentials));
            session = response.getCookies().get("JSESSIONID").getValue();
        }
        return session;
    }
}
