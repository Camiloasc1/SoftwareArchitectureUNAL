package unal.architecture.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class FabricationRecipeDAO {
    @PersistenceContext
    private EntityManager em;

    public void delete(long id) {
        em.createNativeQuery("DELETE FROM FabricationRecipe where id=" + id).executeUpdate();
    }

}
