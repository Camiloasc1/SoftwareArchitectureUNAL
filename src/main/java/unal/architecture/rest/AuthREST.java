package unal.architecture.rest;

import unal.architecture.entity.User;
import unal.architecture.entity.UserCredentials;
import unal.architecture.rest.schemas.Credentials;
import unal.architecture.rest.schemas.PasswordChange;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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
    public User login(Credentials credentials) {
        UserCredentials foundCredentials = em.find(UserCredentials.class, credentials.getUsername());
        if (foundCredentials == null || !foundCredentials.getPassword().equals(credentials.getPassword())) {
            throw new NotAuthorizedException("");
        }
        ctx.getSession().setAttribute("user", foundCredentials.getUser().getId());
        return foundCredentials.getUser();
    }

    @Path("logout")
    @POST
    public void logout() {
        ctx.getSession().setAttribute("user", null);
    }

    @Path("me")
    @GET
    public User me() {
        Long userId = (Long) ctx.getSession().getAttribute("user");
        if (userId == null)
            throw new NotAuthorizedException("");
        return em.find(User.class, userId);
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