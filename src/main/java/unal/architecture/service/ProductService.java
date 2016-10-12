package unal.architecture.service;

import unal.architecture.dao.ProductDAO;
import unal.architecture.entity.Product;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@WebService
@RolesAllowed({"CLIENT"})
public class ProductService {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ProductDAO productDAO;

    @WebMethod
    public List<Product> getProducts() {
        return productDAO.findAll();
    }

    @WebMethod
    public Product getProduct(long id) {
        return em.find(Product.class, id);
    }
}
