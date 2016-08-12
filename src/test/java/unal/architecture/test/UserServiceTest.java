package unal.architecture.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import unal.architecture.entity.User;
import unal.architecture.rest.UserService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    private static final String JNDI = "java:global/SoftwareArchitectureUNAL/UserService";
    private static UserService service;

    @BeforeClass
    public static void beforeClass() throws NamingException {
        Object lookup = EJBContainer.createEJBContainer().getContext().lookup(JNDI);
        assertTrue(lookup instanceof UserService);
        service = (UserService) lookup;
        service.createAdmin();
    }

    @AfterClass
    public static void afterClass() throws NamingException {
    }

    @Test
    public void testFindByUsername() {
        User user = service.findUserByUsername("admin");
        assertNotNull(user);
    }
}
