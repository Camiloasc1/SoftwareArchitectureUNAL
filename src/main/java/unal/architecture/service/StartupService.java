package unal.architecture.service;

import unal.architecture.entity.User;
import unal.architecture.entity.UserPassword;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class StartupService {
    @EJB
    private UserService userService;
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        createAdmin();
    }

    public void createAdmin() {
        if (userService.findByUsername("admin") != null)
            return;

        User admin = new User();
        admin.setUsername("admin");
        admin.setName("admin");
        admin.setEmail("admin@architecture.unal");
        admin.setAdmin(true);
        admin.setWorker(true);
        admin.setSalesman(true);
        em.persist(admin);

        UserPassword password = new UserPassword();
        password.setUser(admin);
        password.setPassword("admin");
        em.persist(password);
    }
}
