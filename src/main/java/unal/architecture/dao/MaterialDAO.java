package unal.architecture.dao;

import unal.architecture.entity.Material;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MaterialDAO {
    @PersistenceContext
    private EntityManager em;

    public List<Material> findAll() {
        TypedQuery<Material> query = em.createNamedQuery("Material.findAll", Material.class);
        return query.getResultList();
    }

    public Material findByName(String name){
        TypedQuery<Material> query = em.createNamedQuery("Material.findByName", Material.class);
        query.setParameter("name", name);
        List<Material> credentials = query.getResultList();
        if (credentials.size() == 0)
            return null;
        else
            return credentials.get(0);
    }

}
