package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.Fabrication;
import unal.architecture.dao.FabricationService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FabricationServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/FabricationService";
    private static FabricationService fabricationService;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof FabricationService);
        fabricationService = (FabricationService) lookup;
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
        List<Fabrication> products = fabricationService.findAll();
        assertTrue(products.isEmpty());
    }
}
