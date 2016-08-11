package unal.architecture.rest;

import unal.architecture.entity.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("greeting")
@Produces(MediaType.APPLICATION_JSON)
public class RestTest {
    @PersistenceContext
    private EntityManager entityManager;

    @GET
    public Student message() {
        Student student = new Student();
        student.setName("Me2");
        //student.setId(99);
        entityManager.persist(student);
        return student;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Student lowerCase(Student student) {
        System.out.println(student);
        student.setId(0);
        entityManager.persist(student);
        return student;
    }
}