package unal.architecture.service;

import unal.architecture.entity.Credit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CreditService {
    @PersistenceContext
    private EntityManager em;

    public List<Credit> findAll() {
        TypedQuery<Credit> query = em.createNamedQuery("Credit.findAll", Credit.class);
        return query.getResultList();
    }
}
