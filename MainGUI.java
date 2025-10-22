package org.example.a3.presentation;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Management Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton clientBtn = new JButton("Manage Clients");
        clientBtn.addActionListener(e -> new ClientManagementWindow());

        JButton productBtn = new JButton("Manage Products");
        productBtn.addActionListener(e -> new ProductManagementWindow());

        JButton orderBtn = new JButton("Manage Orders");
        orderBtn.addActionListener(e -> new OrderManagementWindow());

        add(clientBtn);
        add(productBtn);
        add(orderBtn);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
