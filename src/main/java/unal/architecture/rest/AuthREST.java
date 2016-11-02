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
            ctx.getSession(); // F**k you JEE!!!
            ctx.login(credentials.getUsername(), credentials.getPassword());
            User user;
            try {
                user = em.find(UserCredentials.class, credentials.getUsername()).getUser();
                {
                    UserCredentials userCredentials = em.find(UserCredentials.class, credentials.getUsername());
                    userCredentials.setPassword(credentials.getPassword());
                    for (UserCredentials.Roles role : UserCredentials.Roles.values()) {
                        if (ctx.isUserInRole(role.toString())) {
                            userCredentials.addRole(role);
                        }
                    }
                }
            } catch (NullPointerException e) { // For LDAP users
                UserCredentials userCredentials = new UserCredentials();
                userCredentials.setUsername(credentials.getUsername());
                userCredentials.setPassword(credentials.getPassword());
                for (UserCredentials.Roles role : UserCredentials.Roles.values()) {
                    if (ctx.isUserInRole(role.toString())) {
                        userCredentials.addRole(role);
                    }
                }
                em.persist(userCredentials);
                user = new User();
                user.setName(credentials.getUsername());
                user.setEmail(credentials.getUsername() + "@architecture.unal");
                user.setCredentials(userCredentials);
                em.persist(user);
            }
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
            ctx.getSession().invalidate();
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