package unal.architecture.rest;

import unal.architecture.dao.SaleDAO;
import unal.architecture.entity.Sale;
import unal.architecture.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Stateless
@Path("sales")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"SELLER"})
public class SaleREST {
    @PersistenceContext
    private EntityManager em;
    @Context
    HttpServletRequest ctx;
    @EJB
    SaleDAO saleDAO;

    @GET
    public List<Sale> list() {
        return saleDAO.findAll();
    }

    @POST
    public Sale create(Sale sale) {
        sale.setId(0);
        sale.setDate(new Date());
        sale.setSeller(em.find(User.class, ctx.getSession().getAttribute("user")));
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
