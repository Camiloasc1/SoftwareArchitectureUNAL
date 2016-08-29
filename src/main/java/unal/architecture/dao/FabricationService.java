package unal.architecture.dao;

import unal.architecture.entity.Fabrication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class FabricationService {
    @PersistenceContext
    private EntityManager em;

    public List<Fabrication> findAll() {
        TypedQuery<Fabrication> query = em.createNamedQuery("Fabrication.findAll", Fabrication.class);
        return query.getResultList();
    }
}
