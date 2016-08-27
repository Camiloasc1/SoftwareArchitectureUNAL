package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.Sale;
import unal.architecture.service.SaleService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SaleServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/SaleService";
    private static SaleService saleService;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof SaleService);
        saleService = (SaleService) lookup;
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
        List<Sale> products = saleService.findAll();
        assertTrue(products.isEmpty());
    }
}
