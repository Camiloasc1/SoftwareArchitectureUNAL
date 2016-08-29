package unal.architecture.rest;

import unal.architecture.rest.schemas.Credentials;
import unal.architecture.entity.User;
import unal.architecture.service.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthREST {
    @EJB
    UserService userService;

    @Path("me")
    @GET
    public User me(@Context HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            throw new NotAuthorizedException("");
        return userService.findByUsername(user.getUsername());
    }

    @Path("login")
    @POST
    public User login(Credentials credentials,
                      @Context HttpServletRequest request) {
        User user = userService.findByUsername(credentials.getUsername());
        if (user == null || !userService.checkUserPassword(user, credentials.getPassword())) {
            throw new NotAuthorizedException("");
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