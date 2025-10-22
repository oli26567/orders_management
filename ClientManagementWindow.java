/**
 * GUI window for managing Client operations (Add, Update, Delete, View).
 */
package org.example.a3.presentation;

import org.example.a3.BusinessLogic.ClientBLL;
import org.example.a3.Model.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientManagementWindow extends JFrame {
    private final ClientBLL clientBLL;
    private JTable clientTable;

    private JTextField nameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField ageField;

    /**
     * Constructs a new ClientManagementWindow.
     * Initializes the GUI components and sets up event listeners.
     */
    public ClientManagementWindow() {
        this.clientBLL = new ClientBLL();

        setTitle("Client Management");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        clientTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(clientTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        nameField = new JTextField();
        addressField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        ageField = new JTextField();

        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Address:")); formPanel.add(addressField);
        formPanel.add(new JLabel("Email:")); formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:")); formPanel.add(phoneField);
        formPanel.add(new JLabel("Age:")); formPanel.add(ageField);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        formPanel.add(addBtn); formPanel.add(updateBtn);
        formPanel.add(deleteBtn); formPanel.add(refreshBtn);

        add(formPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                Client c = new Client(0, nameField.getText().trim(), addressField.getText().trim(),
                        emailField.getText().trim(), phoneField.getText().trim(), Integer.parseInt(ageField.getText()));
                clientBLL.insertClient(c);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                showError("Age must be a valid number.");
            } catch (IllegalArgumentException ex) {
                showError("Validation Error: " + ex.getMessage());
            } catch (Exception ex) {
                showError("Failed to add client: " + ex.getMessage());
            }
        });

        updateBtn.addActionListener(e -> {
            int row = clientTable.getSelectedRow();
            if (row == -1) {
                showError("Select a client to update.");
                return;
            }
            try {
                int id = (int) clientTable.getValueAt(row, 0);
                Client c = new Client(id, nameField.getText().trim(), addressField.getText().trim(), // Trimmed
                        emailField.getText().trim(), phoneField.getText().trim(), Integer.parseInt(ageField.getText())); // Trimmed
                clientBLL.updateClient(c);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Client updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                showError("Age must be a valid number.");
            } catch (IllegalArgumentException ex) {
                showError("Validation Error: " + ex.getMessage());
            } catch (NoSuchElementException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Failed to update client: " + ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = clientTable.getSelectedRow();
            if (row == -1) {
                showError("Select a client to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this client?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int id = (int) clientTable.getValueAt(row, 0);
                    clientBLL.deleteClient(id);
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Client deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    showError("Failed to delete client: " + ex.getMessage());
                }
            }
        });

        refreshBtn.addActionListener(e -> refreshTable());

        clientTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = clientTable.getSelectedRow();
                    if (row >= 0 && row < clientTable.getRowCount()) {
                        nameField.setText(String.valueOf(clientTable.getValueAt(row, 1)));
                        addressField.setText(String.valueOf(clientTable.getValueAt(row, 2)));
                        emailField.setText(String.valueOf(clientTable.getValueAt(row, 3)));
                        phoneField.setText(String.valueOf(clientTable.getValueAt(row, 4)));
                        ageField.setText(String.valueOf(clientTable.getValueAt(row, 5)));
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
     * Refreshes the client table with the latest data from the database.
     */
    private void refreshTable() {
        List<Client> clients = clientBLL.findAllClients();
        JTable newTable = TableUtils.buildTableFromList(clients);
        clientTable.setModel(newTable.getModel());
        clientTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Clears all input fields in the client form.
     */
    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        emailField.setText("");
        phoneField.setText("");
        ageField.setText("");
    }

    /**
     * Displays an error message dialog.
     * @param msg The message to display.
     */
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}