package unal.architecture.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Sale.findAll", query = "Select s from Sale s")
})
public class Sale {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private java.sql.Date date;
    @Column(nullable = false)
    private String client;
    @ManyToOne(optional = false)
    private User seller;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
