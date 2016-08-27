package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.Product;
import unal.architecture.service.ProductService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ProductServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/ProductService";
    private static ProductService productService;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof ProductService);
        productService = (ProductService) lookup;
    }

    @AfterClass
    public static void afterClass() throws NamingException {
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void findAll() {
        List<Product> products = productService.findAll();
        assertTrue(products.isEmpty());
    }
}
