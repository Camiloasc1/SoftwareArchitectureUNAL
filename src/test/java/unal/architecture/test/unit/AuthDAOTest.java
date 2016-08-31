package unal.architecture.test.unit;

import org.junit.*;
import unal.architecture.dao.AuthDAO;
import unal.architecture.entity.User;
import unal.architecture.entity.UserCredentials;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthDAOTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/AuthDAO";
    private static AuthDAO authDAO;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof AuthDAO);
        authDAO = (AuthDAO) lookup;
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
    public void testFindByUsername() {
        UserCredentials credentials = authDAO.findByUsername("admin");
        assertNotNull(credentials);
    }
    @Test
    public void testFindByUser() {
        User user = new User();
        user.setId(1);
        UserCredentials credentials = authDAO.findByUser(user);
        assertNotNull(credentials);
    }
}
