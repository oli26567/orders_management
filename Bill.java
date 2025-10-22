/**
 * Represents an immutable Bill object as a Java record.
 * Bills are generated for each order and stored in the Log table.
 * As a record, it automatically provides a constructor, getters, equals(), hashCode(), and toString().
 * It is inherently immutable, fulfilling the assignment requirement.
 */
package org.example.a3.Model;

public record Bill(int billId, int orderId, double totalAmount) {

}
