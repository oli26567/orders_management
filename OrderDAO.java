/**
 * Data Access Object for Order operations.
 * Extends AbstractDAO to inherit generic CRUD functionalities.
 */
package org.example.a3.dao;

import org.example.a3.Model.Order;

public class OrderDAO extends AbstractDAO<Order>{
    /**
     * Constructs a new OrderDAO.
     */
    public OrderDAO() {
        super();
    }
}