/**
 * Abstract Data Access Object (DAO) providing generic CRUD operations using Reflection.
 * This class dynamically generates SQL queries and maps ResultSet data to objects of type T.
 * @param <T> The type of the model object this DAO handles.
 */
package org.example.a3.dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.a3.connection.ConnectionFactory;

public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;
    private final String tableName;

    /**
     * Constructs an AbstractDAO.
     * Determines the generic type T and sets the corresponding table name.
     * Handles special casing for "order" table name.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        String name = type.getSimpleName().toLowerCase();

        if (name.equals("order")) {
            this.tableName = "\"" + name + "\"";
        } else {
            this.tableName = name;
        }
    }

    /**
     * Creates a SELECT query string for finding an object by a specific field.
     * @param field The name of the field to query by (e.g., "id").
     * @return The SQL SELECT query string.
     */
    private String createSelectQuery(String field) {
        return "SELECT * FROM " + tableName + " WHERE " + field + " = ?";
    }

    /**
     * Finds an object by its ID.
     * @param id The ID of the object to find.
     * @return The found object of type T, or null if not found.
     */
    public T findById(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(createSelectQuery("id"))) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<T> result = createObjects(resultSet);
                return result.isEmpty() ? null : result.get(0);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }
        return null;
    }

    /**
     * Creates a list of objects of type T from a ResultSet using reflection.
     * It assumes a default constructor is available and uses setters to populate fields.
     * @param resultSet The ResultSet containing data to map to objects.
     * @return A list of objects of type T.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<?> ctor = null;
        try {
            ctor = type.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            LOGGER.log(Level.SEVERE, "No default constructor found for type " + type.getName(), e);
            return list;
        }

        try {
            while (resultSet.next()) {
                ctor.setAccessible(true); // Allow access to private constructors
                T instance = (T) ctor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);

                    if (value instanceof java.math.BigDecimal && (field.getType().equals(double.class) || field.getType().equals(Double.class))) {
                        value = ((java.math.BigDecimal) value).doubleValue();
                    }

                    try {
                        PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                        Method setter = pd.getWriteMethod();
                        if (setter != null) {
                            setter.invoke(instance, value);
                        }
                    } catch (IntrospectionException | IllegalArgumentException | InvocationTargetException e) {
                        LOGGER.log(Level.WARNING, "Could not set field " + fieldName + " for " + type.getName() + ": " + e.getMessage());
                    }
                }
                list.add(instance);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, "Error creating objects from ResultSet for " + type.getName(), e);
        }
        return list;
    }

    /**
     * Retrieves all objects of type T from the corresponding table.
     * @return A list of all objects of type T.
     */
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            list = createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        }
        return list;
    }

    /**
     * Inserts an object of type T into the database.
     * Dynamically generates the INSERT query based on object fields.
     * Assumes the first field is 'id' and is auto-generated by the DB, so it's skipped in the INSERT statement.
     * @param t The object to insert.
     * @return The inserted object.
     */
    public T insert(T t) {
        Field[] fields = type.getDeclaredFields();
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName());
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append(") VALUES (");
        for (int i = 1; i < fields.length; i++) {
            sb.append("?");
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append(")");

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                ps.setObject(i, fields[i].get(t));
            }

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    try {
                        Field idField = type.getDeclaredField("id");
                        idField.setAccessible(true);
                        idField.set(t, id);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        LOGGER.log(Level.WARNING, "Could not set auto-generated ID for " + type.getName(), e);
                    }
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Insert failed for " + type.getName() + ": " + e.getMessage());
        }
        return t;
    }

    /**
     * Updates an existing object of type T in the database.
     * Dynamically generates the UPDATE query based on object fields.
     * Assumes the first field is 'id' and is used for the WHERE clause.
     * @param t The object to update.
     * @return The updated object.
     */
    public T update(T t) {
        Field[] fields = type.getDeclaredFields();
        StringBuilder sb = new StringBuilder("UPDATE " + tableName + " SET ");
        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName()).append("=?");
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append(" WHERE id=?");

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sb.toString())) {

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                ps.setObject(i, fields[i].get(t));
            }

            fields[0].setAccessible(true);
            ps.setObject(fields.length, fields[0].get(t));

            ps.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Update failed for " + type.getName() + ": " + e.getMessage());
        }
        return t;
    }

    /**
     * Deletes an object of type T from the database by its ID.
     * Dynamically generates the DELETE query.
     * @param id The ID of the object to delete.
     */
    public void deleteById(int id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Delete failed for " + type.getName() + ": " + e.getMessage());
        }
    }
}