package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.User;
import unal.architecture.service.UserService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/UserService";
    private static UserService userService;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof UserService);
        userService = (UserService) lookup;
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
    public void findAdmin() {
        User admin = userService.findByUsername("admin");
        assertNotNull(admin);
        assertTrue(admin.isAdmin());
    }

    @Test
    public void findAndCompareUserPassword() {
        User admin = userService.findByUsername("admin");
        String password = userService.findUserPassword(admin);
        assertNotNull(password);
        assertEquals(password, "admin");
        assertTrue(userService.checkUserPassword(admin, "admin"));
    }
}
