package unal.architecture.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "UserPassword.findByUser", query = "Select p.password from UserPassword p where p.user = :user")
public class UserPassword {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private User user;
    @Column(nullable = false)
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
