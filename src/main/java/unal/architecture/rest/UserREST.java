package unal.architecture.rest;

import unal.architecture.entity.User;
import unal.architecture.entity.UserPassword;
import unal.architecture.service.UserService;

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
    UserService userService;

    @GET
    public List<User> list() {
        return userService.findAll();
    }

    @POST
    public UserPassword create(UserPassword userPassword) {
        userPassword.setId(0);
        em.persist(userPassword.getUser());
        em.persist(userPassword);
        return userPassword;
    }

    @GET
    @Path("{username}")
    public User show(@PathParam("username") String username) {
        return userService.findByUsername(username);
    }

    @PUT
    @Path("{username}")
    public User update(@PathParam("username") String username, User user) {
        user.setId(userService.findByUsername(username).getId());
        user.setUsername(username);
        em.merge(user);
        return user;
    }

    @DELETE
    @Path("{username}")
    public void delete(@PathParam("username") String username) {
        em.remove(userService.findByUsername(username));
        return;
    }
}
