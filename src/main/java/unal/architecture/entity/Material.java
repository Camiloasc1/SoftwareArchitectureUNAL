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

    public float getInventory() {
        return inventory;
    }

    public void setInventory(float inventory) {
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
}
