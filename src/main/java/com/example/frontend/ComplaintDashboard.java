package com.example.frontend;

import com.example.entity.Complaint;
import com.example.service.ComplaintService;
import com.example.service.impl.ComplaintServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ComplaintDashboard extends JFrame
{
    private final ComplaintService service = new ComplaintServiceImpl();
    private final JTextField nameField;
    private final JTextField descField;
    private final JTextField statusField;
    private final JTextField idField;
    private final JTextArea displayArea;

    public ComplaintDashboard() {
        setTitle("Complaint Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Form)
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        descField = new JTextField();
        panel.add(descField);
        panel.add(new JLabel("Status:"));
        statusField = new JTextField();
        panel.add(statusField);
        add(panel, BorderLayout.NORTH);

        // Center Display
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton procBtn = new JButton("Update via Procedure");
        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(procBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Load Dummy Data
        loadDummyData();

        // ================= EVENTS =================
        // Add Complaint
        addBtn.addActionListener(e -> {
            Objects.requireNonNull(e);
            String name = nameField.getText();
            String desc = descField.getText();
            String status = statusField.getText();
            service.addComplaint(new Complaint(name, desc, status));
            showMessage("Complaint Added");
            clearForm();
        });

        // View Complaints
        viewBtn.addActionListener(e -> {
            Objects.requireNonNull(e);
            displayArea.setText("");
            service.getAllComplaints().forEach(c -> displayArea.append(
                    c.getId() + " | " +
                            c.getName() + " | " +
                            c.getDescription() + " | " +
                            c.getStatus() + "\n"
            ));
        });

        // Update Complaint
        updateBtn.addActionListener(e -> {
            Objects.requireNonNull(e);
            try {
                int id = Integer.parseInt(idField.getText());
                String status = statusField.getText();
                service.updateComplaint(id, status);
                showMessage("Updated Successfully");
                displayComplaints();
            } catch (Exception ex) {
                showMessage(ex.getMessage());
            }
        });

        // Delete Complaint
        deleteBtn.addActionListener(e -> {
            Objects.requireNonNull(e);
            try {
                int id = Integer.parseInt(idField.getText());
                service.deleteComplaint(id);
                showMessage("Deleted Successfully");
                displayComplaints();
            } catch (Exception ex) {
                showMessage(ex.getMessage());
            }
        });

        // Procedure Call
        procBtn.addActionListener(e -> {
            Objects.requireNonNull(e);
            try {
                int id = Integer.parseInt(idField.getText());
                String status = statusField.getText();
                service.updateStatusUsingProcedure(id, status);
                showMessage("Updated via Procedure");
                displayComplaints();
            } catch (Exception ex) {
                showMessage(ex.getMessage());
            }
        });
    }

    private void loadDummyData() {
        service.addComplaint(new Complaint("Sony", "TV not working", "OPEN"));
        service.addComplaint(new Complaint("Amit", "Internet slow", "OPEN"));
    }

    private void displayComplaints() {
        displayArea.setText("");
        service.getAllComplaints().forEach(c -> displayArea.append(
                c.getId() + " | " +
                        c.getName() + " | " +
                        c.getDescription() + " | " +
                        c.getStatus() + "\n"
        ));
    }

    private void clearForm() {
        nameField.setText("");
        descField.setText("");
        statusField.setText("");
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        Objects.requireNonNull(args);
        SwingUtilities.invokeLater(() -> new ComplaintDashboard().setVisible(true));
    }
}

