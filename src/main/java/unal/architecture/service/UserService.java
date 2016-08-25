package unal.architecture.service;

import unal.architecture.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager em;

    public User findByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (users.size() == 0)
            return null;
        else
            return users.get(0);
    }

    public String findUserPassword(User user) {
        TypedQuery<String> query = em.createNamedQuery("UserPassword.findByUser", String.class);
        query.setParameter("user", user);
        String password = query.getSingleResult();
        return password;
    }

    public boolean checkUserPassword(User user, String password) {
        return findUserPassword(user).equals(password);
    }
}
