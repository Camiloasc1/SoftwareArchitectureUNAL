package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Fabrication;
import unal.architecture.entity.Product;
import unal.architecture.test.util.ITUtil;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class FabricationRESTIT {
    private static final String PATH = "fabrications";
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
    public void crudFabrication() {
        Response response;
        Fabrication fabrication;

        String session = ITUtil.getAdminSession();

        Product product = new Product();
        product.setName("Test Product");
        product.setInventory(0);
        product.setPrice(10.0f);
        product = client.target("http://localhost:8080/SoftwareArchitectureUNAL/products")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(product), Product.class);
        assertNotNull(product);

        //Create
        fabrication = new Fabrication();
        fabrication.setProduct(product);
        fabrication.setQuantity(1);

        fabrication = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(fabrication), Fabrication.class);
        assertNotNull(fabrication);

        //Read
        fabrication = client.target(URI)
                .path(fabrication.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Fabrication.class);
        assertNotNull(fabrication);
        assertEquals(product, fabrication.getProduct());
        assertEquals(1, fabrication.getQuantity());

        //Update
        fabrication.setQuantity(10);
        fabrication = client.target(URI)
                .path(fabrication.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .put(Entity.json(fabrication), Fabrication.class);
        assertNotNull(fabrication);
        assertEquals(10, fabrication.getQuantity());

        //Delete
        response = client.target(URI)
                .path(fabrication.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .delete();
        assertEquals(204, response.getStatus());

        fabrication = client.target(URI)
                .path(fabrication.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Fabrication.class);
        assertNull(fabrication);
    }
}