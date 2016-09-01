package unal.architecture.rest;

import unal.architecture.entity.User;
import unal.architecture.entity.UserCredentials;
import unal.architecture.rest.schemas.Credentials;
import unal.architecture.rest.schemas.PasswordChange;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthREST {
    @PersistenceContext
    private EntityManager em;
    @Context
    HttpServletRequest ctx;

    @Path("login")
    @POST
    public Response login(Credentials credentials) {
        try {
            ctx.login(credentials.getUsername(), credentials.getPassword());
            User user = em.find(UserCredentials.class, credentials.getUsername()).getUser();
            ctx.getSession().setAttribute("user", user.getId());
            return Response.ok(user).build();
        } catch (ServletException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @Path("logout")
    @POST
    public void logout() {
        try {
            ctx.logout();
            ctx.getSession().setAttribute("user", null);
        } catch (ServletException e) {
        }
    }

    @Path("me")
    @GET
    public Response me() {
        Long userId = (Long) ctx.getSession().getAttribute("user");
        if (userId == null)
            return Response.status(Response.Status.FORBIDDEN).build();
        return Response.ok(em.find(User.class, userId)).build();
    }

    @Path("passwd")
    @PUT
    public void update(PasswordChange change) {
        UserCredentials userCredentials = em.find(UserCredentials.class, change.getUsername());
        if (userCredentials == null || !userCredentials.getPassword().equals(change.getOldPassword())) {
            throw new NotAuthorizedException("");
        }
        userCredentials.setPassword(change.getNewPassword());
        em.merge(userCredentials);
        return;
    }
}