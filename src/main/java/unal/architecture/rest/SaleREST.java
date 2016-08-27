package unal.architecture.rest;

import unal.architecture.entity.Material;
import unal.architecture.entity.Sale;
import unal.architecture.service.SaleService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("sales")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaleREST {
    @PersistenceContext
    private EntityManager em;
    @EJB
    SaleService saleService;

    @GET
    public List<Sale> list() {
        return saleService.findAll();
    }

    @POST
    public Sale create(Sale sale) {
        sale.setId(0);
        em.persist(sale);
        return sale;
    }

    @GET
    @Path("{id}")
    public Sale show(@PathParam("id") long id) {
        return em.find(Sale.class, id);
    }

    @PUT
    @Path("{id}")
    public Sale update(@PathParam("id") long id, Sale sale) {
        sale.setId(id);
        em.merge(sale);
        return sale;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        em.remove(em.find(Sale.class, id));
        return;
    }
}
