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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public float getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(float requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }
}
