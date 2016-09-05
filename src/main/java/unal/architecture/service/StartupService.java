package unal.architecture.service;

import unal.architecture.dao.AuthDAO;
import unal.architecture.dao.MaterialDAO;
import unal.architecture.entity.Material;
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
    @EJB
    private MaterialDAO materialDAO;

    @PostConstruct
    public void init() {
        createAdmin();
        createProducts();
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


    public void createProducts(){
        String [] names = {
             "Aluminio",
             "Madera",
             "Vidrio templado",
             "Cartón",
             "Cuero",
             "Plastico"
        };

        for(int i=0 ; i<names.length ; i++){
            Material m = new Material();
            m.setId(0);
            m.setName(names[i]);
            m.setInventory((int)(Math.random()*100));
            m.setProvider("Proveedor "+i);
            m.setRawMaterial(true);
            m.setSupply(false);
            int aux = (int)(Math.random()*800000);
            m.setPrice(aux-aux%1000);

            if(materialDAO.findByName(m.getName()) == null) {
                em.persist(m);
            }
        }
    }

}
