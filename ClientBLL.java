/**
 * Business Logic Layer for Client operations.
 * Handles validation and delegates to the ClientDAO for database interactions.
 */
package org.example.a3.BusinessLogic;

import org.example.a3.Model.Client;
import org.example.a3.dao.ClientDAO;
import org.example.a3.BusinessLogic.validators.Validator;
import org.example.a3.BusinessLogic.validators.ClientAgeValidator;
import org.example.a3.BusinessLogic.validators.EmailValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private final ClientDAO clientDAO;
    private final List<Validator<Client>> validators;

    /**
     * Constructs a new ClientBLL instance.
     * Initializes the ClientDAO and sets up a list of validators for Client objects.
     */
    public ClientBLL() {
        this.clientDAO = new ClientDAO();
        this.validators = new ArrayList<>();
        // Add specific validator instances
        this.validators.add(new EmailValidator());
        this.validators.add(new ClientAgeValidator());
    }

    /**
     * Finds a client by their ID.
     * @param id The ID of the client to find.
     * @return The found Client object.
     * @throws NoSuchElementException if no client with the given ID is found.
     */
    public Client findClientById(int id) {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return c;
    }

    /**
     * Retrieves all clients from the database.
     * @return A list of all Client objects.
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Inserts a new client into the database after validating it.
     * @param c The Client object to insert.
     * @return The inserted Client object.
     * @throws IllegalArgumentException if the client fails any validation rules.
     */
    public Client insertClient(Client c) {
        validators.forEach(v -> v.validate(c));
        return clientDAO.insert(c);
    }

    /**
     * Updates an existing client in the database after validating it.
     * @param c The Client object to update.
     * @return The updated Client object.
     * @throws IllegalArgumentException if the client fails any validation rules.
     */
    public Client updateClient(Client c) {
        validators.forEach(v -> v.validate(c));
        return clientDAO.update(c);
    }

    /**
     * Deletes a client from the database by their ID.
     * @param id The ID of the client to delete.
     */
    public void deleteClient(int id) {
        clientDAO.deleteById(id);
    }
}