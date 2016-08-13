package unal.architecture.test;

import org.junit.*;
import unal.architecture.service.StartupService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import static org.junit.Assert.assertTrue;

public class StartupServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/StartupService";
    private static StartupService service;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        System.out.println(lookup.getClass());
        assertTrue(lookup instanceof StartupService);
        service = (StartupService) lookup;
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
