/**
 * Validates the age of a client.
 * Ensures the client's age falls within a predefined range.
 */
package org.example.a3.BusinessLogic.validators;

import org.example.a3.Model.Client;

public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 7;
    private static final int MAX_AGE = 30;

    /**
     * Validates the age of the given client.
     * @param t The client to validate.
     * @throws IllegalArgumentException if the client's age is outside the allowed range.
     */
    public void validate(Client t) {
        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected! Age must be between " + MIN_AGE + " and " + MAX_AGE + ".");
        }
    }
}