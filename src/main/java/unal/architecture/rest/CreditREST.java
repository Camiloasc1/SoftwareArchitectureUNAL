package unal.architecture.rest;

import unal.architecture.entity.Credit;
import unal.architecture.dao.CreditService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Stateless
@Path("credits")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreditREST {
    @PersistenceContext
    private EntityManager em;
    @EJB
    CreditService creditService;

    @GET
    public List<Credit> list() {
        return creditService.findAll();
    }

    @POST
    public Credit create(Credit credit) {
        credit.setId(0);
        credit.setDate(new Date());
        em.persist(credit);
        return credit;
    }

    @GET
    @Path("{id}")
    public Credit show(@PathParam("id") long id) {
        return em.find(Credit.class, id);
    }

    @PUT
    @Path("{id}")
    public Credit update(@PathParam("id") long id, Credit credit) {
        credit.setId(id);
        em.merge(credit);
        return credit;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        em.remove(em.find(Credit.class, id));
        return;
    }
}
