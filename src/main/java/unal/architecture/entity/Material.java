package unal.architecture.entity;

import javax.persistence.*;

@Entity
public class Material {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private float inventory;
    @Column(nullable = false)
    private boolean supply;
    @Column(nullable = false)
    private boolean rawMaterial;
    @Column(nullable = false)
    private String provider;
}
