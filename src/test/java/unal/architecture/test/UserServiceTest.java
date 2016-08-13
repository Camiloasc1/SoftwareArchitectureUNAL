package unal.architecture.test;

import org.junit.*;
import unal.architecture.entity.User;
import unal.architecture.service.UserService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/UserService";
    private static UserService service;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        System.out.println(lookup.getClass());
        assertTrue(lookup instanceof UserService);
        service = (UserService) lookup;
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
    public void testAdmin() {
        User admin = service.findByUsername("admin");
        assertNotNull(admin);
        assertTrue(admin.isAdmin());
    }
}
