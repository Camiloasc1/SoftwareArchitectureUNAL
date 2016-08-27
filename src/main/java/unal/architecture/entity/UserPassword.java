package unal.architecture.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "UserPassword.findByUser", query = "Select p.password from UserPassword p where p.user = :user")
public class UserPassword {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne(optional = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPassword)) return false;

        UserPassword password1 = (UserPassword) o;

        if (id != password1.id) return false;
        if (user != null ? !user.equals(password1.user) : password1.user != null) return false;
        return password != null ? password.equals(password1.password) : password1.password == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
