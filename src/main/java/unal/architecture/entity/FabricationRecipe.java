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
    private int requiredQuantity;

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

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FabricationRecipe)) return false;

        FabricationRecipe that = (FabricationRecipe) o;

        if (id != that.id) return false;
        if (requiredQuantity != that.requiredQuantity) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        return material != null ? material.equals(that.material) : that.material == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (material != null ? material.hashCode() : 0);
        result = 31 * result + requiredQuantity;
        return result;
    }
}
