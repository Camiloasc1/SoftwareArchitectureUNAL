package unal.architecture.service;

import unal.architecture.entity.User;

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
    private EntityManager entityManager;

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
        admin.setPassword("admin");
        admin.setAdmin(true);
        admin.setWorker(true);
        admin.setSalesman(true);
        entityManager.persist(admin);
    }
}
