package unal.architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "UserCredentials.findAll", query = "Select uc from UserCredentials uc")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCredentials {

    public enum Roles {
        ADMIN, WORKER, SELLER, GUEST
    }

    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToOne(optional = false, mappedBy = "credentials")
    private User user;
    @ElementCollection(targetClass = Roles.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "role", nullable = false)
    private Set<Roles> roles = new HashSet<Roles>();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public boolean addRole(Roles roles) {
        return this.roles.add(roles);
    }

    public boolean removeRole(Object o) {
        return roles.remove(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCredentials)) return false;

        UserCredentials that = (UserCredentials) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
