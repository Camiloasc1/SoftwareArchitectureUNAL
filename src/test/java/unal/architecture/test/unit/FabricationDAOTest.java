package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.dao.FabricationDAO;
import unal.architecture.entity.Fabrication;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FabricationDAOTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/FabricationDAO";
    private static FabricationDAO fabricationDAO;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof FabricationDAO);
        fabricationDAO = (FabricationDAO) lookup;
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
        List<Fabrication> products = fabricationDAO.findAll();
        assertTrue(products.isEmpty());
    }
}
