/**
 * GUI window for managing Order operations (Place Order, View Orders).
 * Allows users to select existing clients and products to create new orders.
 */
package org.example.a3.presentation;

import org.example.a3.BusinessLogic.OrderBLL;
import org.example.a3.BusinessLogic.ClientBLL;
import org.example.a3.BusinessLogic.ProductBLL;
import org.example.a3.Model.Client;
import org.example.a3.Model.Product;
import org.example.a3.Model.Order;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderManagementWindow extends JFrame {
    private final OrderBLL orderBLL;
    private final ClientBLL clientBLL;
    private final ProductBLL productBLL;

    private JComboBox<Client> clientCombo;
    private JComboBox<Product> productCombo;
    private JTextField quantityField;
    private JTable orderTable;
    private JScrollPane tableScrollPane;

    /**
     * Constructs a new OrderManagementWindow.
     * Initializes GUI components and sets up event listeners.
     */
    public OrderManagementWindow() {
        this.orderBLL = new OrderBLL();
        this.clientBLL = new ClientBLL();
        this.productBLL = new ProductBLL();

        setTitle("Order Management");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        orderTable = new JTable();
        tableScrollPane = new JScrollPane(orderTable);
        add(tableScrollPane, BorderLayout.CENTER);
        refreshTable();

        JPanel controlPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Added gaps

        clientCombo = new JComboBox<>();
        productCombo = new JComboBox<>();
        refreshDropdowns();

        quantityField = new JTextField();

        controlPanel.add(new JLabel("Client:")); controlPanel.add(clientCombo);
        controlPanel.add(new JLabel("Product:")); controlPanel.add(productCombo);
        controlPanel.add(new JLabel("Quantity:")); controlPanel.add(quantityField);

        JButton orderBtn = new JButton("Place Order");
        orderBtn.addActionListener(e -> placeOrder());

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> {
            refreshDropdowns();
            refreshTable();
            JOptionPane.showMessageDialog(this, "Data refreshed!", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        controlPanel.add(orderBtn);
        controlPanel.add(refreshBtn);

        add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Handles the logic for placing a new order.
     * Validates input, checks stock, and inserts the order and a corresponding bill.
     */
    private void placeOrder() {
        try {
            Client client = (Client) clientCombo.getSelectedItem();
            Product product = (Product) productCombo.getSelectedItem();
            String quantityText = quantityField.getText().trim();

            if (client == null) {
                showError("Please select a client.");
                return;
            }
            if (product == null) {
                showError("Please select a product.");
                return;
            }

            if (quantityText.isEmpty()) {
                showError("Quantity must not be empty.");
                return;
            }

            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showError("Quantity must be greater than 0.");
                return;
            }

            orderBLL.insertOrder(new Order(0, client.getId(), product.getId(), quantity));
            refreshTable();
            quantityField.setText("");
            JOptionPane.showMessageDialog(this, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException nfe) {
            showError("Quantity must be a valid number.");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            showError("Order Error: " + e.getMessage());
        } catch (Exception e) {
            showError("An unexpected error occurred while placing order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the order table with the latest data from the database.
     */
    private void refreshTable() {
        List<Order> orders = orderBLL.findAllOrders();
        JTable newTable = TableUtils.buildTableFromList(orders);
        tableScrollPane.setViewportView(newTable);
        orderTable = newTable; // Update the reference to the new table
        revalidate();
        repaint();
    }

    /**
     * Refreshes the client and product dropdowns with the latest data.
     */
    private void refreshDropdowns() {
        clientCombo.setModel(new DefaultComboBoxModel<>(clientBLL.findAllClients().toArray(new Client[0])));
        productCombo.setModel(new DefaultComboBoxModel<>(productBLL.findAllProducts().toArray(new Product[0])));
    }

    /**
     * Displays an error message dialog.
     * @param msg The message to display.
     */
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}