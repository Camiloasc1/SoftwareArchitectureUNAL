package unal.architecture.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Fabrication.findAll", query = "Select f from Fabrication f")
})
public class Fabrication {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(optional = false)
    private Product product;
    @ManyToOne(optional = false)
    private User worker;
    @Column(nullable = false)
    private java.sql.Date date;
    @Column(nullable = false)
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
