/**
 * Data Access Object for Product operations.
 * Extends AbstractDAO to inherit generic CRUD functionalities.
 */
package org.example.a3.dao;

import org.example.a3.Model.Product;

public class ProductDAO extends AbstractDAO<Product>{
    /**
     * Constructs a new ProductDAO.
     */
    public ProductDAO() {
        super();
    }
}