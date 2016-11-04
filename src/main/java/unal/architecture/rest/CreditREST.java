package unal.architecture.rest;

import unal.architecture.dao.CreditDAO;
import unal.architecture.entity.Credit;
import unal.architecture.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Stateless
@Path("credits")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN"})
public class CreditREST {
    @PersistenceContext
    private EntityManager em;
    @Context
    HttpServletRequest ctx;
    @EJB
    CreditDAO creditDAO;

    @GET
    public List<Credit> list() {
        return creditDAO.findAll();
    }

    @POST
    public Response create(Credit credit) {
        int numberOfPayments = (int)(Math.random()*48) + 1;
        System.out.println("Amount= "+credit.getAmount()+" cuotas= "+numberOfPayments);

        //String response = CreditsWSClient.createCreditWS("Em-Amigable",credit.getAmount(),numberOfPayments);
        String response;
        if(Math.random()<0.5)
           response  = "El producto NO ha sido creado.";
        else
            response = "El producto ha sido creado.";

        if(!response.equals("El producto ha sido creado.")){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }else{
            credit.setId(0);
            credit.setDate(new Date());
            credit.setUser(em.find(User.class, ctx.getSession().getAttribute("user")));
            em.persist(credit);
            return Response.ok(credit).build();
        }
    }

    @GET
    @Path("{id}")
    public Credit show(@PathParam("id") long id) {
        return em.find(Credit.class, id);
    }

    @PUT
    @Path("{id}")
    public Credit update(@PathParam("id") long id, Credit credit) {
        credit.setId(id);
        em.merge(credit);
        return credit;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        em.remove(em.find(Credit.class, id));
        return;
    }
}
