package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.dao.SaleDAO;
import unal.architecture.entity.Sale;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SaleDAOTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/SaleDAO";
    private static SaleDAO saleDAO;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof SaleDAO);
        saleDAO = (SaleDAO) lookup;
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
        List<Sale> products = saleDAO.findAll();
        assertTrue(products.isEmpty());
    }
}
