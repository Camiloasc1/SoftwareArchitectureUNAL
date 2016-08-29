package unal.architecture.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "Select p from Product p")
})
public class Product {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int inventory;
    @Column(nullable = false)
    private float price;

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
        if (inventory < 0) inventory = 0;
        this.inventory = inventory;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price < 0.0) price = 0.0f;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (inventory != product.inventory) return false;
        if (Float.compare(product.price, price) != 0) return false;
        return name != null ? name.equals(product.name) : product.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + inventory;
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }
}
