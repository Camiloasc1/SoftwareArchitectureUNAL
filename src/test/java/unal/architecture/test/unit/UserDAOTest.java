package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.entity.User;
import unal.architecture.dao.UserDAO;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/UserDAO";
    private static UserDAO userDAO;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof UserDAO);
        userDAO = (UserDAO) lookup;
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
        List<User> users = userDAO.findAll();
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
