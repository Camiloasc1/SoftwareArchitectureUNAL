package unal.architecture.test.integration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.*;
import unal.architecture.entity.Product;
import unal.architecture.test.util.ITUtil;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class ProductRESTIT {
    private static final String PATH = "products";
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
        Product product;

        String session = ITUtil.getAdminSession();

        //Create
        product = new Product();
        product.setName("Test Product");
        product.setInventory(0);
        product.setPrice(10.0f);

        product = client.target(URI)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .post(Entity.json(product), Product.class);
        assertNotNull(product);

        //Read
        product = client.target(URI)
                .path(product.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Product.class);
        assertNotNull(product);
        assertEquals("Test Product", product.getName());
        assertEquals(0, product.getInventory());
        assertEquals(10.0f, product.getPrice(), 0.1);

        //Update
        product.setInventory(100);
        product = client.target(URI)
                .path(product.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .put(Entity.json(product), Product.class);
        assertNotNull(product);
        assertEquals(100, product.getInventory());

        //Delete
        response = client.target(URI)
                .path(product.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .delete();
        assertEquals(204, response.getStatus());

        product = client.target(URI)
                .path(product.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", session)
                .get(Product.class);
        assertNull(product);
    }
}