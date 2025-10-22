/**
 * Factory class for managing database connections.
 * Provides methods to get and close connections, statements, and result sets.
 * Uses a singleton pattern for the connection factory.
 */
package org.example.a3.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DBURL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "1234";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Private constructor to enforce singleton pattern.
     * Attempts to load the JDBC driver.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "PostgreSQL JDBC Driver not found", e);
        }
    }

    /**
     * Creates a new database connection.
     * @return A new Connection object, or null if a connection error occurs.
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database", e);
        }
        return connection;
    }

    /**
     * Retrieves a database connection from the singleton instance.
     * @return A database Connection.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes a database connection.
     * @param connection The Connection to close.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection", e);
            }
        }
    }

    /**
     * Closes a SQL statement.
     * @param statement The Statement to close.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement", e);
            }
        }
    }

    /**
     * Closes a SQL ResultSet.
     * @param resultSet The ResultSet to close.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the ResultSet", e);
            }
        }
    }
}
