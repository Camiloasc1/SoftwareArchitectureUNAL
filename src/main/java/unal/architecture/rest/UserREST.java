package unal.architecture.rest;

import unal.architecture.entity.User;
import unal.architecture.service.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserREST {
    @PersistenceContext
    private EntityManager em;
    @EJB
    UserService userService;

    @GET
    public List<User> list() {
        return null;
    }

    @POST
    public boolean create(User user) {
        return false;
    }

    @GET
    @Path("{username}")
    public User show(@PathParam("username") String username) {
        return null;
    }

    @PUT
    @Path("{username}")
    public boolean update(@PathParam("username") String username, User user) {
        return false;
    }

    @DELETE
    @Path("{username}")
    public boolean delete(@PathParam("username") String username) {
        return false;
    }
}
