package unal.architecture.rest;

import unal.architecture.dao.FabricationDAO;
import unal.architecture.dao.FabricationRecipeDAO;
import unal.architecture.entity.Credit;
import unal.architecture.entity.FabricationRecipe;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Stateless
@Path("recipes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FabricationRecipeREST {

    @PersistenceContext
    private EntityManager em;
    @EJB
    FabricationRecipeDAO fabricationDAO;

    @GET
    public List<FabricationRecipe> list(){
        return fabricationDAO.findAll();
    }

    @GET
    @Path("productID={pid}&materialID={mid}")
    public FabricationRecipe show(@PathParam("pid") long pid, @PathParam("mid") long mid){
        return fabricationDAO.findByProductIDAndMaterialID(mid,pid);
    }

    @GET
    @Path("productID={pid}")
    public List<FabricationRecipe> findByProductID(@PathParam("pid") long pid){
        return fabricationDAO.findByProductID(pid);
    }


    @POST
    public FabricationRecipe create(FabricationRecipe fabricationRecipe){
        throw new UnsupportedOperationException("Not implemented yet");
        //fabricationRecipe.setId(0);
        //em.persist(fabricationRecipe);
        //return fabricationRecipe;
    }
}
