/**
 * Validator interface for business logic validation.
 * @param <T> The type of object to be validated.
 */
package org.example.a3.BusinessLogic.validators;

public interface Validator<T> {
    /**
     * Validates the given object.
     * @param t The object to validate.
     * @throws IllegalArgumentException if the object is invalid.
     */
    public void validate(T t);
}