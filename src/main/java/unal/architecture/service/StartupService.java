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

        User admin = new User();
        admin.setName("admin");
        admin.setEmail("admin@architecture.unal");
        admin.setAdmin(true);
        admin.setWorker(true);
        admin.setSalesman(true);
        em.persist(admin);

        UserCredentials credentials = new UserCredentials();
        credentials.setUser(admin);
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        em.persist(credentials);
    }
}
