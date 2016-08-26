package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Material;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class MaterialRESTIT {
    private static final String URI = "http://localhost:8080/SoftwareArchitectureUNAL/materials";
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

        //Create
        material = new Material();
        material.setName("Test Product");
        material.setInventory(0);
        material.setSupply(false);
        material.setRawMaterial(false);
        material.setProvider("Test Provider");

        material = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(material), Material.class);
        assertNotNull(material);

        //Read
        material = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Material.class);
        assertNotNull(material);
        assertEquals("Test Product", material.getName());
        assertEquals(0, material.getInventory());
        assertEquals(false,material.isSupply());
        assertEquals(false,material.isRawMaterial());
        assertEquals("Test Provider",material.getProvider());

        //Update
        material.setInventory(100);
        material = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(material), Material.class);
        assertNotNull(material);
        assertEquals(100, material.getInventory());

        //Delete
        response = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(204, response.getStatus());

        material = client.target(URI)
                .path(material.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Material.class);
        assertNull(material);
    }
}