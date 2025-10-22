/**
 * Business Logic Layer for Product operations.
 * Delegates to the ProductDAO for database interactions.
 */
package org.example.a3.BusinessLogic;

import org.example.a3.Model.Product;
import org.example.a3.dao.ProductDAO;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private final ProductDAO productDAO;

    /**
     * Constructs a new ProductBLL instance.
     * Initializes the ProductDAO.
     */
    public ProductBLL() {
        this.productDAO = new ProductDAO();
    }

    /**
     * Finds a product by its ID.
     * @param id The ID of the product to find.
     * @return The found Product object.
     * @throws NoSuchElementException if no product with the given ID is found.
     */
    public Product findProductById(int id) {
        Product p = productDAO.findById(id);
        if (p == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return p;
    }

    /**
     * Retrieves all products from the database.
     * @return A list of all Product objects.
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Inserts a new product into the database.
     * @param p The Product object to insert.
     * @return The inserted Product object.
     */
    public Product insertProduct(Product p) {
        return productDAO.insert(p);
    }

    /**
     * Updates an existing product in the database.
     * @param p The Product object to update.
     * @return The updated Product object.
     */
    public Product updateProduct(Product p) {
        return productDAO.update(p);
    }

    /**
     * Deletes a product from the database by its ID.
     * @param id The ID of the product to delete.
     */
    public void deleteProduct(int id) {
        productDAO.deleteById(id);
    }
}