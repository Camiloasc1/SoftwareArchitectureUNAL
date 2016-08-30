package unal.architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "UserCredentials.findByUsername", query = "Select uc from UserCredentials uc where uc.username = :username")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCredentials {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne(optional = false)
    private User user;
    @Column(unique = true, nullable = false)
    private String username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (!(o instanceof UserCredentials)) return false;

        UserCredentials password1 = (UserCredentials) o;

        if (id != password1.id) return false;
        if (user != null ? !user.equals(password1.user) : password1.user != null) return false;
        if (username != null ? !username.equals(password1.username) : password1.username != null) return false;
        return password != null ? password.equals(password1.password) : password1.password == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
