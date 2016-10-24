package unal.architecture.startup;

import unal.architecture.dao.MaterialDAO;
import unal.architecture.dao.ProductDAO;
import unal.architecture.dao.SaleDAO;
import unal.architecture.dao.UserDAO;
import unal.architecture.entity.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
public class StartupService {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private MaterialDAO materialDAO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private SaleDAO saleDAO;

    @EJB
    private UserDAO userDAO;

    @PostConstruct
    public void init() {
        if (em.find(UserCredentials.class, "admin") != null)
            return;

        createAdmin();
        populateDatabase();
    }

    public void createAdmin() {
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

    public void createMaterials() {

        String[] names = {
                "Aluminio",
                "Madera",
                "Plastico",
                "Vidrio",
                "Espejo",
                "Cuero",
                "Tela",
                "Bombillos",
                "Tornillos",
                "Tuercas",
        };


        Material material;
        for (int i = 0; i < names.length; i++) {
            if (materialDAO.findByName(names[i]) != null) continue;

            material = new Material();
            material.setId(0);
            material.setName(names[i]);
            material.setProvider("Proveedor " + (int) (Math.random() * 10 + 1));
            boolean isSupply = Math.random() < 0.5;
            material.setSupply(isSupply);
            material.setPrice((int) (Math.random() * 100000 + 1000));
            material.setRawMaterial(!isSupply);
            material.setInventory((int) (Math.random() * 1000 + 10));

            em.persist(material);
        }


    }

    public void createProducts() {

        String[] names = {
                "Espejo retrovisor",
                "Rin",
                "Limpia parabrisas",
                "Copas para rin",
                "Ambientador",
                "Radio",
                "Silleteria en cuero",
                "Equipo de sonido",
                "Farola",
                "Silla para niños",
                "Seguro para puertas"
        };

        Product product;

        for (String name : names) {
            if (productDAO.findByName(name) != null) continue;

            product = new Product();
            product.setName(name);
            product.setPrice((int) (Math.random() * 100000 + 1000));
            product.setInventory((int) (Math.random() * 1000 + 10));
            product.setRecipes(createRecipes(product));
            product.setId(0);
            em.persist(product);
        }

    }

    private ArrayList<FabricationRecipe> createRecipes(Product p) {
        ArrayList<FabricationRecipe> fabricationRecipes = new ArrayList<FabricationRecipe>();
        FabricationRecipe fabricationRecipe = new FabricationRecipe();
        fabricationRecipe.setProduct(p);
        fabricationRecipe.setId(0);
        fabricationRecipe.setRequiredQuantity((int) (Math.random() * 100 + 1));
        fabricationRecipe.setMaterial(materialDAO.findByName("Aluminio"));
        fabricationRecipes.add(fabricationRecipe);
        return fabricationRecipes;
    }

    public void createUsers() {
        String[] names = {
                "amrondonp",
                "dicrojasch",
                "keacastropo",
                "datovard",
                "caasanchezcu",
                "worker",
                "seller",
                "client"
        };

        User user;

        for (String name : names) {
            if (em.find(UserCredentials.class, name) != null) continue;
            UserCredentials userCredentials = new UserCredentials();
            userCredentials.setUsername(name);
            userCredentials.setPassword(name);

            if (name.equals("worker")) userCredentials.addRole(UserCredentials.Roles.WORKER);
            else if (name.equals("seller")) userCredentials.addRole(UserCredentials.Roles.SELLER);
            else if (name.equals("client")) userCredentials.addRole(UserCredentials.Roles.CLIENT);
            else {
                userCredentials.addRole(UserCredentials.Roles.ADMIN);
                userCredentials.addRole(UserCredentials.Roles.WORKER);
                userCredentials.addRole(UserCredentials.Roles.SELLER);
                userCredentials.addRole(UserCredentials.Roles.CLIENT);
            }


            em.persist(userCredentials);

            user = new User();
            user.setName(name);
            user.setId(0);
            user.setEmail(name + "@architecture.unal");
            user.setCredentials(userCredentials);

            em.persist(user);
        }
    }


    public void createSales() {
        int nOfSales = 20;
        Sale sale;

        if (saleDAO.findAll().size() > 0) return;

        List<User> userList = userDAO.findAll();
        for (int j = 0; j < userList.size(); j++) {
            if (userList.get(j).getName().equals("client")) {
                userList.remove(j);
                break;
            }
        }

        for (int i = 0; i < nOfSales; i++) {

            List<Product> products = productDAO.findAll();

            sale = new Sale();

            SaleDetail saleDetail1 = new SaleDetail();
            saleDetail1.setId(0);
            saleDetail1.setProduct(products.get((int) (Math.random() * products.size())));
            saleDetail1.setPrice((int) (Math.random() * 100000 + 10000));
            saleDetail1.setQuantity((int) (Math.random() * 100 + 1));
            saleDetail1.setSale(sale);

            SaleDetail saleDetail2 = new SaleDetail();
            saleDetail2.setId(0);
            saleDetail2.setProduct(products.get((int) (Math.random() * products.size())));
            saleDetail2.setPrice((int) (Math.random() * 100000 + 10000));
            saleDetail2.setQuantity((int) (Math.random() * 100 + 1));
            saleDetail2.setSale(sale);

            ArrayList<SaleDetail> saleDetails = new ArrayList<SaleDetail>();
            saleDetails.add(saleDetail1);
            saleDetails.add(saleDetail2);


            sale.setSaleDetail(saleDetails);
            sale.setClient("client");

            sale.setSeller(userList.get((int) (Math.random() * userList.size())));
            sale.setId(0);
            sale.setDate(new Date());

            em.persist(sale);
        }
    }

    public void populateDatabase() {
        createMaterials();
        createProducts();
        createUsers();
        createSales();
    }
}
