package unal.architecture.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Material.findAll", query = "Select m from Material m")
})
public class Material {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int inventory;
    @Column(nullable = false)
    private boolean supply;
    @Column(nullable = false)
    private boolean rawMaterial;
    @Column(nullable = false)
    private String provider;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public boolean isSupply() {
        return supply;
    }

    public void setSupply(boolean supply) {
        this.supply = supply;
    }

    public boolean isRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(boolean rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;

        Material material = (Material) o;

        if (id != material.id) return false;
        if (inventory != material.inventory) return false;
        if (supply != material.supply) return false;
        if (rawMaterial != material.rawMaterial) return false;
        if (name != null ? !name.equals(material.name) : material.name != null) return false;
        return provider != null ? provider.equals(material.provider) : material.provider == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + inventory;
        result = 31 * result + (supply ? 1 : 0);
        result = 31 * result + (rawMaterial ? 1 : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }
}
