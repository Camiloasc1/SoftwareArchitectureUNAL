package unal.architecture.dao;

import unal.architecture.entity.Sale;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class SaleService {
    @PersistenceContext
    private EntityManager em;

    public List<Sale> findAll() {
        TypedQuery<Sale> query = em.createNamedQuery("Sale.findAll", Sale.class);
        return query.getResultList();
    }
}
