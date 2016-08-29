package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.Material;
import unal.architecture.dao.MaterialService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MaterialServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/MaterialService";
    private static MaterialService materialService;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof MaterialService);
        materialService = (MaterialService) lookup;
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
        List<Material> products = materialService.findAll();
        assertTrue(products.isEmpty());
    }
}
