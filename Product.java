/**
 * Represents a product in the Orders Management system.
 * This class stores product details such as ID, name, description, price, and stock.
 */
package org.example.a3.Model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;

    /**
     * Default constructor for Product.
     */
    public Product() {}

    /**
     * Constructs a new Product with specified details.
     * @param id The unique identifier for the product.
     * @param name The name of the product.
     * @param description A brief description of the product.
     * @param price The price of the product.
     * @param stock The current stock quantity of the product.
     */
    public Product(int id, String name, String description, double price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Returns the ID of the product.
     * @return The product ID.
     */
    public int getId() { return id; }
    /**
     * Sets the ID of the product.
     * @param id The new product ID.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the name of the product.
     * @return The product name.
     */
    public String getName() { return name; }
    /**
     * Sets the name of the product.
     * @param name The new product name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the description of the product.
     * @return The product description.
     */
    public String getDescription() { return description; }
    /**
     * Sets the description of the product.
     * @param description The new product description.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Returns the price of the product.
     * @return The product price.
     */
    public double getPrice() { return price; }
    /**
     * Sets the price of the product.
     * @param price The new product price.
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Returns the current stock quantity of the product.
     * @return The product stock.
     */
    public int getStock() { return stock; }
    /**
     * Sets the current stock quantity of the product.
     * @param stock The new stock quantity.
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * Returns a string representation of the product.
     * @return A string containing product details.
     */
    @Override
    public String toString() {
        return id + ". " + name + ", price=" + price + ", stock=" + stock;
    }
}