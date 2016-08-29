package unal.architecture.rest;

import unal.architecture.entity.User;
import unal.architecture.dao.UserDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserREST {
    @PersistenceContext
    private EntityManager em;
    @EJB
    UserDAO userDAO;

    @GET
    public List<User> list() {
        return userDAO.findAll();
    }

    @POST
    public User create(User user) {
        user.setId(0);
        em.persist(user);
        return user;
    }

    @GET
    @Path("{id}")
    public User show(@PathParam("id") long id) {
        return em.find(User.class, id);
    }

    @PUT
    @Path("{id}")
    public User update(@PathParam("id") long id, User user) {
        user.setId(id);
        em.merge(user);
        return user;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        em.remove(em.find(User.class, id));
        return;
    }
}
