/**
 * GUI window for managing Product operations (Add, Update, Delete, View).
 */
package org.example.a3.presentation;

import org.example.a3.BusinessLogic.ProductBLL;
import org.example.a3.Model.Product;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductManagementWindow extends JFrame {
    private final ProductBLL productBLL;
    private JTable productTable;

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField stockField;

    /**
     * Constructs a new ProductManagementWindow.
     * Initializes the GUI components and sets up event listeners.
     */
    public ProductManagementWindow() {
        this.productBLL = new ProductBLL();

        setTitle("Product Management");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        productTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5)); // Added gaps

        nameField = new JTextField();
        descriptionField = new JTextField();
        priceField = new JTextField();
        stockField = new JTextField();

        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Description:")); formPanel.add(descriptionField);
        formPanel.add(new JLabel("Price:")); formPanel.add(priceField);
        formPanel.add(new JLabel("Stock:")); formPanel.add(stockField);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        formPanel.add(addBtn); formPanel.add(updateBtn);
        formPanel.add(deleteBtn); formPanel.add(refreshBtn);

        add(formPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                Product p = new Product(0, nameField.getText(), descriptionField.getText(),
                        Double.parseDouble(priceField.getText()), Integer.parseInt(stockField.getText()));
                productBLL.insertProduct(p);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                showError("Price and Stock must be valid numbers.");
            } catch (Exception ex) {
                showError("Error adding product: " + ex.getMessage());
            }
        });

        updateBtn.addActionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row == -1) {
                showError("Select a product to update.");
                return;
            }
            try {
                int id = (int) productTable.getValueAt(row, 0);
                Product p = new Product(id, nameField.getText(), descriptionField.getText(),
                        Double.parseDouble(priceField.getText()), Integer.parseInt(stockField.getText()));
                productBLL.updateProduct(p);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                showError("Price and Stock must be valid numbers.");
            } catch (NoSuchElementException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Error updating product: " + ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row == -1) {
                showError("Select a product to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int id = (int) productTable.getValueAt(row, 0);
                    productBLL.deleteProduct(id);
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    showError("Error deleting product: " + ex.getMessage());
                }
            }
        });

        refreshBtn.addActionListener(e -> refreshTable());

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = productTable.getSelectedRow();
                    if (row >= 0 && row < productTable.getRowCount()) {
                        nameField.setText(String.valueOf(productTable.getValueAt(row, 1)));
                        descriptionField.setText(String.valueOf(productTable.getValueAt(row, 2)));
                        priceField.setText(String.valueOf(productTable.getValueAt(row, 3)));
                        stockField.setText(String.valueOf(productTable.getValueAt(row, 4)));
                    } else {
                        clearFields();
                    }
                }
            }
        });

        refreshTable();
        setVisible(true);
    }

    /**
     * Refreshes the product table with the latest data from the database.
     */
    private void refreshTable() {
        List<Product> products = productBLL.findAllProducts();
        JTable newTable = TableUtils.buildTableFromList(products);
        productTable.setModel(newTable.getModel());
        productTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Clears all input fields in the product form.
     */
    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        stockField.setText("");
    }

    /**
     * Displays an error message dialog.
     * @param msg The message to display.
     */
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}