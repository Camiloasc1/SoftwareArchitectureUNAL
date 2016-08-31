package unal.architecture.rest;

import unal.architecture.dao.FabricationDAO;
import unal.architecture.entity.Fabrication;
import unal.architecture.entity.User;

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
@Path("fabrications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FabricationREST {
    @PersistenceContext
    private EntityManager em;
    @Context
    HttpServletRequest ctx;
    @EJB
    FabricationDAO fabricationDAO;

    @GET
    public List<Fabrication> list() {
        return fabricationDAO.findAll();
    }

    @POST
    public Fabrication create(Fabrication fabrication) {
        fabrication.setId(0);
        fabrication.setDate(new Date());
        fabrication.setWorker(em.find(User.class, ctx.getSession().getAttribute("user")));
        em.persist(fabrication);
        return fabrication;
    }

    @GET
    @Path("{id}")
    public Fabrication show(@PathParam("id") long id) {
        return em.find(Fabrication.class, id);
    }

    @PUT
    @Path("{id}")
    public Fabrication update(@PathParam("id") long id, Fabrication fabrication) {
        fabrication.setId(id);
        em.merge(fabrication);
        return fabrication;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        em.remove(em.find(Fabrication.class, id));
        return;
    }
}
