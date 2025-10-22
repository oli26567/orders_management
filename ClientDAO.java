/**
 * Data Access Object for Client operations.
 * Extends AbstractDAO to inherit generic CRUD functionalities.
 */
package org.example.a3.dao;

import org.example.a3.Model.Client;

public class ClientDAO extends AbstractDAO<Client> {
    /**
     * Constructs a new ClientDAO.
     */
    public ClientDAO() {
        super();
    }
}