package unal.architecture.entity;

import javax.persistence.*;

@Entity
public class FabricationRecipe {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(optional = false)
    private Product product;
    @ManyToOne(optional = false)
    private Material material;
    @Column(nullable = false)
    private float requiredQuantity;
}
