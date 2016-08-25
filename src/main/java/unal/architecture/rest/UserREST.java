package unal.architecture.rest;

import unal.architecture.entity.Credentials;
import unal.architecture.entity.User;
import unal.architecture.service.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserREST {
    @EJB
    UserService userService;
    @PersistenceContext
    private EntityManager entityManager;

    @Path("me")
    @GET
    public Object me(@Context HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }

    @Path("login")
    @POST
    public User login(Credentials credentials,
                      @Context HttpServletRequest request) {
        User user = userService.findByUsername(credentials.getUsername());
        if (user == null || !userService.checkUserPassword(user, credentials.getPassword())) {
            return null;
        }
        request.getSession().setAttribute("user", user);
        return user;
    }

    @Path("logout")
    @POST
    public void logout(@Context HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
    }
}