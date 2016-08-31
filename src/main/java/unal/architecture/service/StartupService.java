package unal.architecture.service;

import unal.architecture.dao.AuthDAO;
import unal.architecture.entity.User;
import unal.architecture.entity.UserCredentials;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class StartupService {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private AuthDAO authDAO;

    @PostConstruct
    public void init() {
        createAdmin();
    }

    public void createAdmin() {
        if (authDAO.findByUsername("admin") != null)
            return;


        UserCredentials credentials = new UserCredentials();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        credentials.addRole(UserCredentials.Roles.ADMIN);
        credentials.addRole(UserCredentials.Roles.WORKER);
        credentials.addRole(UserCredentials.Roles.SELLER);
        credentials.addRole(UserCredentials.Roles.GUEST);

        em.persist(credentials);

        User admin = new User();
        admin.setName("admin");
        admin.setEmail("admin@architecture.unal");
        admin.setCredentials(credentials);

        em.persist(admin);
    }
}
