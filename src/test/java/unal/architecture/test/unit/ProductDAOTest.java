package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.dao.ProductDAO;
import unal.architecture.entity.Product;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ProductDAOTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/ProductDAO";
    private static ProductDAO productDAO;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof ProductDAO);
        productDAO = (ProductDAO) lookup;
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
        List<Product> products = productDAO.findAll();
        //We have added entities
        assertTrue(!products.isEmpty());
    }
}
