package unal.architecture.dao;

import unal.architecture.entity.Sale;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class SaleDAO {
    @PersistenceContext
    private EntityManager em;

    public List<Sale> findAll() {
        TypedQuery<Sale> query = em.createNamedQuery("Sale.findAll", Sale.class);
        return query.getResultList();
    }

    public List<Sale> findByDate( Date date1, Date date2 ) {
        TypedQuery<Sale> query = em.createNamedQuery("Sale.findByDate", Sale.class).setParameter("date1", date1 ).setParameter("date2", date2);
        return query.getResultList();
    }

    public List<Sale> findAllBySeller(long id) {
        TypedQuery<Sale> query = em.createNamedQuery("Sale.findAllBySeller", Sale.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

}



