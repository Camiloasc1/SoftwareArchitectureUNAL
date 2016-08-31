package unal.architecture.dao;

import unal.architecture.entity.User;
import unal.architecture.entity.UserCredentials;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AuthDAO {
    @PersistenceContext
    private EntityManager em;

    public UserCredentials findByUsername(String username) {
        TypedQuery<UserCredentials> query = em.createNamedQuery("UserCredentials.findByUsername", UserCredentials.class);
        query.setParameter("username", username);
        List<UserCredentials> credentials = query.getResultList();
        if (credentials.size() == 0)
            return null;
        else
            return credentials.get(0);
    }
}
