package unal.architecture.dao;

import unal.architecture.entity.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProductDAO {
    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        return query.getResultList();
    }
}
