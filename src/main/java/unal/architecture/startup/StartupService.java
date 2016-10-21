package unal.architecture.startup;

import unal.architecture.entity.User;
import unal.architecture.entity.UserCredentials;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class StartupService {
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        createAdmin();
    }

    public void createAdmin() {
        if (em.find(UserCredentials.class, "admin") != null)
            return;


        UserCredentials credentials = new UserCredentials();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        credentials.addRole(UserCredentials.Roles.ADMIN);
        credentials.addRole(UserCredentials.Roles.WORKER);
        credentials.addRole(UserCredentials.Roles.SELLER);
        credentials.addRole(UserCredentials.Roles.CLIENT);

        em.persist(credentials);

        User admin = new User();
        admin.setName("admin");
        admin.setEmail("admin@architecture.unal");
        admin.setCredentials(credentials);

        em.persist(admin);
    }
}
