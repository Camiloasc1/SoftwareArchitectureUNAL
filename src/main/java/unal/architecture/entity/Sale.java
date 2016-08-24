package unal.architecture.entity;

import javax.persistence.*;

@Entity
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
}
