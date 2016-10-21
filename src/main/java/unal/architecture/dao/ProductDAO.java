package unal.architecture.dao;

import unal.architecture.entity.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Type;
import java.util.List;

@Stateless
public class ProductDAO {
    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        return query.getResultList();
    }

    public Product findByName(String name) {
        TypedQuery<Product> query = em.createNamedQuery("Product.findByName", Product.class);
        query.setParameter("name", name);
        List<Product> credentials = query.getResultList();
        if (credentials.size() == 0)
            return null;
        else
            return credentials.get(0);
    }

    public void remove(long id) {
        em.createNativeQuery("delete from FabricationRecipe where product_id=" + id).executeUpdate();
        em.remove(em.find(Product.class, id));
    }
}
