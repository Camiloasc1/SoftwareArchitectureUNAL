package unal.architecture.rest;

import unal.architecture.dao.SaleDAO;
import unal.architecture.entity.Sale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Stateless
@Path("sales")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaleREST {
    @PersistenceContext
    private EntityManager em;
    @EJB
    SaleDAO saleDAO;

    @GET
    public List<Sale> list() {
        return saleDAO.findAll();
    }

    @POST
    public Sale create(Sale sale) {
        sale.setId(0);
        sale.setDate(new Date());
        em.persist(sale);
        return sale;
    }

    @GET
    @Path("{id}")
    public Sale show(@PathParam("id") long id) {
        return em.find(Sale.class, id);
    }

    @GET
    @Path("stats/{date}")
    public List<Sale> byDate(@PathParam("date") String d ) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date1 = formatter.parse(d+" 00:00:00");
        Date date2 = formatter.parse(d+" 23:59:59");
        return saleDAO.findByDate( date1, date2 );
    }

    @GET
    @Path("seller/{id}")
    public List<Sale> showSeller(@PathParam("id") long id) {
        return saleDAO.findAllBySeller(id);
    }

    @PUT
    @Path("{id}")
    public Sale update(@PathParam("id") long id, Sale sale) {
        sale.setId(id);
        em.merge(sale);
        return sale;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        em.remove(em.find(Sale.class, id));
        return;
    }
}
