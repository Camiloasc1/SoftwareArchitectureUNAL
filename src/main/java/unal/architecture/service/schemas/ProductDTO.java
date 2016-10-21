package unal.architecture.service.schemas;

import unal.architecture.entity.Product;

public class ProductDTO {
    private long id;
    private String name;
    private int inventory;
    private float price;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.inventory = product.getInventory();
        this.price = product.getPrice();
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
