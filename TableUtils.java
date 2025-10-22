/**
 * Utility class for building JTables from a list of objects using reflection.
 * It extracts column names from object properties and populates table rows with their values.
 */
package org.example.a3.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class TableUtils {

    /**
     * Builds a JTable from a list of objects.
     * Uses reflection to determine column headers from object field names
     * and populates rows with corresponding field values.
     * @param objects The list of objects to display in the table.
     * @param <T> The type of objects in the list.
     * @return A JTable populated with data from the list. Returns an empty JTable if the list is null or empty.
     */
    public static <T> JTable buildTableFromList(List<T> objects) {
        if (objects == null || objects.isEmpty()) {
            return new JTable(new DefaultTableModel()); // Return empty table with no columns
        }

        T sample = objects.get(0);
        Field[] fields = sample.getClass().getDeclaredFields();

        // Extract column names from field names
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true); // Allow access to private fields
            columnNames[i] = fields[i].getName();
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate table rows with object data
        for (T obj : objects) {
            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                try {
                    rowData[i] = fields[i].get(obj); // Get field value using reflection
                } catch (IllegalAccessException e) {
                    // Log the error, but continue processing other fields/objects
                    System.err.println("Error accessing field " + fields[i].getName() + ": " + e.getMessage());
                    rowData[i] = "N/A"; // Placeholder for inaccessible fields
                }
            }
            model.addRow(rowData);
        }

        return new JTable(model);
    }
}
