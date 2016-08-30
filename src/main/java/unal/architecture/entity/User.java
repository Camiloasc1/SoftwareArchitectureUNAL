package unal.architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "Select u from User u"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column()
    private String email;
    @Column(nullable = false)
    private boolean admin;
    @Column(nullable = false)
    private boolean worker;
    @Column(nullable = false)
    private boolean salesman;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isWorker() {
        return worker;
    }

    public void setWorker(boolean worker) {
        this.worker = worker;
    }

    public boolean isSalesman() {
        return salesman;
    }

    public void setSalesman(boolean salesman) {
        this.salesman = salesman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (admin != user.admin) return false;
        if (worker != user.worker) return false;
        if (salesman != user.salesman) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (worker ? 1 : 0);
        result = 31 * result + (salesman ? 1 : 0);
        return result;
    }
}
