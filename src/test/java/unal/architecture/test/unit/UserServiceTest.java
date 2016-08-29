package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.User;
import unal.architecture.dao.UserService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.*;

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
    public void findAll() {
        List<User> users = userService.findAll();
        assertTrue(!users.isEmpty());

        boolean found = false;
        for (User u : users) {
            if (u.getName().equals("admin")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
}
