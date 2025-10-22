/**
 * Main application controller for demonstrating business logic operations.
 * This class can be used for initial setup or testing.
 */
package org.example.a3.presentation;

import org.example.a3.BusinessLogic.ClientBLL;
import org.example.a3.BusinessLogic.OrderBLL;
import org.example.a3.BusinessLogic.ProductBLL;
import org.example.a3.Model.Client;
import org.example.a3.Model.Order;
import org.example.a3.Model.Product;

import java.util.List;

public class Controller {
    private final ClientBLL clientBLL;
    private final ProductBLL productBLL;
    private final OrderBLL orderBLL;

    /**
     * Constructs a new Controller instance.
     * Initializes the Business Logic Layer components.
     */
    public Controller() {
        this.clientBLL = new ClientBLL();
        this.productBLL = new ProductBLL();
        this.orderBLL = new OrderBLL();
    }

    /**
     * Performs a series of demo operations:
     * - Inserts a sample client and product.
     * - Places an order.
     * - Displays all clients, products, and orders to the console.
     */
    public void demoOperations() {
        System.out.println("--- Starting Demo Operations ---");
        try {
            Client c = new Client(0, "Ion Pop", "Str. Unirii 5", "ion.pop@email.com", "0712345678", 25);
            Client insertedClient = clientBLL.insertClient(c);
            System.out.println("Inserted Client: " + insertedClient);

            Product p = new Product(0, "Mouse", "Wireless optical mouse", 99.99, 10);
            Product insertedProduct = productBLL.insertProduct(p);
            System.out.println("Inserted Product: " + insertedProduct);

            Order o = new Order(0, insertedClient.getId(), insertedProduct.getId(), 2);
            Order insertedOrder = orderBLL.insertOrder(o);
            System.out.println("Placed Order: " + insertedOrder);

            List<Client> clients = clientBLL.findAllClients();
            System.out.println("\n--- All Clients ---");
            clients.forEach(System.out::println);

            List<Product> products = productBLL.findAllProducts();
            System.out.println("\n--- All Products ---");
            products.forEach(System.out::println);

            System.out.println("\n--- All Orders ---");
            orderBLL.findAllOrders().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Error during demo operations: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("--- Demo Operations Finished ---");
    }
}