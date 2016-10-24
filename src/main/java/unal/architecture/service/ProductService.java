package unal.architecture.service;

import unal.architecture.dao.ProductDAO;
import unal.architecture.entity.Product;
import unal.architecture.service.schemas.ProductDTO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@WebService
public class ProductService {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ProductDAO productDAO;

    @WebMethod
    public List<ProductDTO> getProducts() {
        List<Product> products = productDAO.findAll();
        final List<ProductDTO> productDTOs = new ArrayList<ProductDTO>(products.size());
//        products.forEach(p -> productDTOs.add(new ProductDTO(p))); // 1.8
        for (Product p : products) {
            productDTOs.add(new ProductDTO(p));
        }
        return productDTOs;
    }

    @WebMethod
    public ProductDTO getProduct(@WebParam(name = "id") long id) {
        return new ProductDTO(em.find(Product.class, id));
    }

    @WebMethod
    public boolean orderProduct(@WebParam(name = "id") long id, @WebParam(name = "amount") int amount) {
        Product product = em.find(Product.class, id);
        if (product == null) {
            return false;
        }
        int inventory = product.getInventory() - amount;
        if (amount<0 || inventory < 0) {
            return false;
        }
        product.setInventory(inventory);
        return true;
    }
}
