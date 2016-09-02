package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Material;
import unal.architecture.test.util.ITUtil;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class MaterialRESTIT {
    private static final String PATH = "materials";
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
    public void crudProduct() {
        Response response;
        Material material;

        String session = ITUtil.getAdminSession();

        //Create
        material = new Material();
        material.setName("Test Product");
        material.setInventory(0);
        material.setSupply(false);
        material.setRawMaterial(false);
        material.setProvider("Test Provider");
        material.setPrice(0);

        material = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(material), Material.class);
        assertNotNull(material);

        //Read
        material = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Material.class);
        assertNotNull(material);
        assertEquals("Test Product", material.getName());
        assertEquals(0, material.getInventory());
        assertEquals(false, material.isSupply());
        assertEquals(false, material.isRawMaterial());
        assertEquals("Test Provider", material.getProvider());

        //Update
        material.setInventory(100);
        material = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .put(Entity.json(material), Material.class);
        assertNotNull(material);
        assertEquals(100, material.getInventory());

        //Delete
        response = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .delete();
        assertEquals(204, response.getStatus());

        material = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Material.class);
        assertNull(material);
    }
}