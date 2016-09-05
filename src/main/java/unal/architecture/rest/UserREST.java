package unal.architecture.rest;

import unal.architecture.dao.AuthDAO;
import unal.architecture.dao.UserDAO;
import unal.architecture.entity.User;
import unal.architecture.entity.UserCredentials;

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
    @EJB
    AuthDAO authDAO;

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

    @GET
    @Path("username/{username}")
    public User checkUsername(@PathParam("username") String username) {
        UserCredentials userCredentials = authDAO.findByUsername(username);
        if( userCredentials == null )
            return null;
        return userCredentials.getUser();
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
        User user = em.find(User.class, id);
        UserCredentials userCredentials = authDAO.findByUserId(user.getId());
        userCredentials.setUser(null);
        em.remove(userCredentials);
        em.remove(user);
        return;
    }
}
