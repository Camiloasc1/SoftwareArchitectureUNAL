package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.Credit;
import unal.architecture.dao.CreditService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CreditServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/CreditService";
    private static CreditService creditService;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof CreditService);
        creditService = (CreditService) lookup;
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
        List<Credit> products = creditService.findAll();
        assertTrue(products.isEmpty());
    }
}
