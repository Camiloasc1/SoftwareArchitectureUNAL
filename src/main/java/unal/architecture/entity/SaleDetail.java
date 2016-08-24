package unal.architecture.entity;

import javax.persistence.*;

@Entity
public class SaleDetail {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(optional = false)
    private Sale sale;
    @ManyToOne(optional = false)
    private Product product;
    @Column(nullable = false)
    private int cuantity;
    @Column(nullable = false)
    private float price;
}
