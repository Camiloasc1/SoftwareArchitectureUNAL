package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.service.StartupService;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import static org.junit.Assert.assertTrue;

public class StartupServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/StartupService";
    @EJB
    private static StartupService startupService;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof StartupService);
        startupService = (StartupService) lookup;
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
}
