package unal.architecture.rest;

import unal.architecture.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Path("login")
    @POST
    public User login(Credentials credentials) {
        User user = findByUsername(credentials.username);
        System.out.println(user);
        if (user != null && user.getPassword().compareTo(credentials.password) == 0)
            return user;
        return null;
    }

    @Path("logout")
    @POST
    public User logout(User user) {
        System.out.println(user);
        user.setId(0);
        entityManager.persist(user);
        return user;
    }

    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (users.size() == 0)
            return null;
        else
            return users.get(0);
    }

    public void createAdmin() {
        if (findByUsername("admin") != null)
            return;

        User admin = new User();
        admin.setUsername("admin");
        admin.setName("admin");
        admin.setPassword("admin");
        entityManager.persist(admin);
    }
}

class Credentials {
    public String username;
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}