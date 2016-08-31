package unal.architecture.dao;

import unal.architecture.entity.Fabrication;
import unal.architecture.entity.FabricationRecipe;
import unal.architecture.entity.Material;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.ListAttribute;

import java.util.List;

@Stateless
public class FabricationRecipeDAO{

    @PersistenceContext
    private EntityManager em;

    public List<FabricationRecipe> findAll(){
        TypedQuery<FabricationRecipe> query = em.createNamedQuery("FabricationRecipe.findAll", FabricationRecipe.class);
        return query.getResultList();
    }

    public FabricationRecipe findByProductIDAndMaterialID(long mid, long pid){
        TypedQuery<FabricationRecipe> query = em.createNamedQuery("FabricationRecipe.findAllByProductIDAndMaterialID", FabricationRecipe.class);
        query.setParameter("mid", mid);
        query.setParameter("pid", pid);
        return query.getSingleResult();
    }

    public List<FabricationRecipe> findByProductID(long pid){
        TypedQuery<FabricationRecipe> query = em.createNamedQuery("FabricationRecipe.findAllByProductID", FabricationRecipe.class);
        query.setParameter("pid",pid);
        return query.getResultList();
    }

}
