/**
 * Business Logic Layer for Order operations.
 * Handles order creation, including stock checks and bill generation.
 */
package org.example.a3.BusinessLogic;

import org.example.a3.Model.Bill;
import org.example.a3.Model.Order;
import org.example.a3.Model.Product;
import org.example.a3.dao.BillDAO;
import org.example.a3.dao.OrderDAO;
import org.example.a3.dao.ProductDAO;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final BillDAO billDAO;

    /**
     * Constructs a new OrderBLL instance.
     * Initializes DAOs for Order, Product, and Bill.
     */
    public OrderBLL() {
        this.orderDAO = new OrderDAO();
        this.productDAO = new ProductDAO();
        this.billDAO = new BillDAO();
    }

    /**
     * Inserts a new order into the database.
     * Performs stock validation and decrements product stock upon successful order.
     * Also generates and stores a bill for the order.
     * @param o The Order object to insert.
     * @return The inserted Order object.
     * @throws NoSuchElementException if the product associated with the order is not found.
     * @throws IllegalArgumentException if there is insufficient stock for the order.
     */
    public Order insertOrder(Order o) {
        Product product = productDAO.findById(o.getProductId());
        if (product == null) {
            throw new NoSuchElementException("Product with ID " + o.getProductId() + " not found.");
        }

        if (product.getStock() < o.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available for product: " + product.getName() + ". Available: " + product.getStock() + ", Requested: " + o.getQuantity());
        }

        product.setStock(product.getStock() - o.getQuantity());
        productDAO.update(product);

        Order insertedOrder = orderDAO.insert(o);

        double total = o.getQuantity() * product.getPrice();
        Bill bill = new Bill(0, insertedOrder.getId(), total);
        billDAO.insert(bill);

        return insertedOrder;
    }

    /**
     * Retrieves all orders from the database.
     * @return A list of all Order objects.
     */
    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }
}