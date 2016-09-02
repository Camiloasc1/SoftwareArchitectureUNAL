package unal.architecture.rest;

import unal.architecture.dao.ProductDAO;
import unal.architecture.entity.Product;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN"})
public class ProductREST {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ProductDAO productDAO;

    @GET
    public List<Product> list() {
        return productDAO.findAll();
    }

    @POST
    public Product create(Product product) {
        product.setId(0);
        em.persist(product);
        return product;
    }

    @GET
    @Path("{id}")
    public Product show(@PathParam("id") long id) {
        return em.find(Product.class, id);
    }

    @PUT
    @Path("{id}")
    public Product update(@PathParam("id") long id, Product product) {
        product.setId(id);
        em.merge(product);
        return product;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        em.remove(em.find(Product.class, id));
        return;
    }
}
