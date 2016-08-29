package unal.architecture.dao;

import unal.architecture.entity.Material;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MaterialService {
    @PersistenceContext
    private EntityManager em;

    public List<Material> findAll() {
        TypedQuery<Material> query = em.createNamedQuery("Material.findAll", Material.class);
        return query.getResultList();
    }
}
