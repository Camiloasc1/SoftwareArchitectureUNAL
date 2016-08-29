package unal.architecture.rest;

import unal.architecture.dao.AuthService;
import unal.architecture.entity.UserCredentials;
import unal.architecture.rest.schemas.Credentials;
import unal.architecture.entity.User;
import unal.architecture.dao.UserService;
import unal.architecture.rest.schemas.PasswordChange;

import javax.ejb.EJB;
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
    @EJB
    AuthService authService;

    @Path("login")
    @POST
    public User login(Credentials credentials, @Context HttpServletRequest request) {
        UserCredentials userCredentials = authService.findByUsername(credentials.getUsername());
        if (userCredentials == null || !userCredentials.getPassword().equals(credentials.getPassword())) {
            throw new NotAuthorizedException("");
        }
        request.getSession().setAttribute("user", userCredentials.getUser().getId());
        return userCredentials.getUser();
    }

    @Path("logout")
    @POST
    public void logout(@Context HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
    }

    @Path("me")
    @GET
    public User me(@Context HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("user");
        if (userId == null)
            throw new NotAuthorizedException("");
        return em.find(User.class, userId);
    }

    @Path("passwd")
    @PUT
    public void update(PasswordChange change) {
        UserCredentials userCredentials = authService.findByUsername(change.getUsername());
        if (userCredentials == null || !userCredentials.getPassword().equals(change.getOldPassword())) {
            throw new NotAuthorizedException("");
        }
        userCredentials.setPassword(change.getNewPassword());
        em.merge(userCredentials);
        return;
    }
}