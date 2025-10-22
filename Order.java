/**
 * Represents an order in the Orders Management system.
 * This class stores order details such as ID, client ID, product ID, and quantity.
 */
package org.example.a3.Model;

public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    /**
     * Default constructor for Order.
     */
    public Order() {}

    /**
     * Constructs a new Order with specified details.
     * @param id The unique identifier for the order.
     * @param clientId The ID of the client who placed the order.
     * @param productId The ID of the product ordered.
     * @param quantity The quantity of the product ordered.
     */
    public Order(int id, int clientId, int productId, int quantity) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Returns the ID of the order.
     * @return The order ID.
     */
    public int getId() { return id; }
    /**
     * Sets the ID of the order.
     * @param id The new order ID.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the client ID associated with the order.
     * @return The client ID.
     */
    public int getClientId() { return clientId; }
    /**
     * Sets the client ID associated with the order.
     * @param clientId The new client ID.
     */
    public void setClientId(int clientId) { this.clientId = clientId; }

    /**
     * Returns the product ID associated with the order.
     * @return The product ID.
     */
    public int getProductId() { return productId; }
    /**
     * Sets the product ID associated with the order.
     * @param productId The new product ID.
     */
    public void setProductId(int productId) { this.productId = productId; }

    /**
     * Returns the quantity of the product in the order.
     * @return The quantity.
     */
    public int getQuantity() { return quantity; }
    /**
     * Sets the quantity of the product in the order.
     * @param quantity The new quantity.
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Returns a string representation of the order.
     * @return A string containing order details.
     */
    @Override
    public String toString() {
        return "Order [id=" + id + ", clientId=" + clientId + ", productId=" + productId + ", quantity=" + quantity + "]";
    }
}