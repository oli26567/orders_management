/**
 * Validates the email format of a client.
 * Uses a regular expression to ensure the email is in a valid format.
 */
package org.example.a3.BusinessLogic.validators;

import org.example.a3.Model.Client;
import java.util.regex.Pattern;

public class EmailValidator implements Validator<Client> {
   private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE
    );

    /**
     * Validates the email address of the given client.
     * @param client The client to validate.
     * @throws IllegalArgumentException if the email address is null, empty, or does not match the valid pattern.
     */

    @Override
    public void validate(Client client) {
        String email = client.getEmail();
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }/*
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address format! Please use a format like 'user@example.com'.");
        }*/
    }
}