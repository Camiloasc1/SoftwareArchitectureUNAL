package unal.architecture.entity;

import javax.persistence.*;

@Entity
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
}
