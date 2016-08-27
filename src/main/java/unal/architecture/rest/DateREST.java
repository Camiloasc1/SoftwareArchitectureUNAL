package unal.architecture.rest;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Stateless
@Path("date")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DateREST {

    @GET
    public Element list() {
        return new Element();
    }

    @POST
    public Element create(Element element) {
        return element;
    }
}

class Element {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    java.util.Date date;

    public Element() {
        this.date = new java.util.Date(System.currentTimeMillis());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}