package unal.architecture.service;

import unal.architecture.dao.ProductDAO;
import unal.architecture.entity.Product;
import unal.architecture.service.schemas.ProductDTO;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Stateless
@WebService
@Path("rest/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"CLIENT"})
public class ProductService {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ProductDAO productDAO;

    @GET
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

    @GET
    @Path("{id}")
    @WebMethod
    public ProductDTO getProduct(@PathParam("id") long id) {
        return new ProductDTO(em.find(Product.class, id));
    }

    @WebMethod
    public boolean orderProduct(long id, int amount) {
        Product product = em.find(Product.class, id);
        if (product == null) {
            return false;
        }
        int inventory = product.getInventory() - amount;
        if (inventory < 0) {
            return false;
        }
        product.setInventory(inventory);
        return true;
    }

    @POST
    @Path("{id}")
    @WebMethod(exclude = true)
    public Response orderProduct(@PathParam("id") long id, Amount amount) {
        if (orderProduct(id, amount.getAmount()))
            return Response.ok().build();
        else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}

class Amount {
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}