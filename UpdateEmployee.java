import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame {

    private JTextField idField, nameField, salaryField, emailField, phoneField;
    private JCheckBox chkName, chkSalary, chkEmail, chkPhone;

    public UpdateEmployee() {
        // Set up the frame
        setTitle("Update Employee Details");
        setBounds(100, 100, 400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Heading label
        JLabel lblHeading = new JLabel("Update Employee Details");
        lblHeading.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeading.setBounds(100, 30, 250, 30);
        getContentPane().add(lblHeading);

        // Employee ID label and text field
        JLabel lblID = new JLabel("Employee ID:");
        lblID.setBounds(30, 80, 100, 25);
        getContentPane().add(lblID);

        idField = new JTextField();
        idField.setBounds(140, 80, 150, 25);
        getContentPane().add(idField);

        // Button to fetch employee details
        JButton btnFetchDetails = new JButton("Fetch Details");
        btnFetchDetails.setBounds(150, 120, 150, 30);
        getContentPane().add(btnFetchDetails);

        // Name label and text field
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 160, 100, 25);
        getContentPane().add(lblName);

        nameField = new JTextField();
        nameField.setBounds(140, 160, 150, 25);
        nameField.setEnabled(false);  // Initially disabled
        getContentPane().add(nameField);

        // Salary label and text field
        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setBounds(30, 200, 100, 25);
        getContentPane().add(lblSalary);

        salaryField = new JTextField();
        salaryField.setBounds(140, 200, 150, 25);
        salaryField.setEnabled(false);  // Initially disabled
        getContentPane().add(salaryField);

        // Email label and text field
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 240, 100, 25);
        getContentPane().add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(140, 240, 150, 25);
        emailField.setEnabled(false);  // Initially disabled
        getContentPane().add(emailField);

        // Phone label and text field
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(30, 280, 100, 25);
        getContentPane().add(lblPhone);

        phoneField = new JTextField();
        phoneField.setBounds(140, 280, 150, 25);
        phoneField.setEnabled(false);  // Initially disabled
        getContentPane().add(phoneField);

        // Checkboxes to select fields for updating
        chkName = new JCheckBox("Name");
        chkName.setBounds(30, 320, 100, 25);
        getContentPane().add(chkName);

        chkSalary = new JCheckBox("Salary");
        chkSalary.setBounds(140, 320, 100, 25);
        getContentPane().add(chkSalary);

        chkEmail = new JCheckBox("Email");
        chkEmail.setBounds(30, 360, 100, 25);
        getContentPane().add(chkEmail);

        chkPhone = new JCheckBox("Phone");
        chkPhone.setBounds(140, 360, 100, 25);
        getContentPane().add(chkPhone);

        // Button to update employee details
        JButton btnUpdateEmployee = new JButton("Update Employee");
        btnUpdateEmployee.setBounds(150, 400, 150, 30);
        getContentPane().add(btnUpdateEmployee);

        // Button actions
        btnFetchDetails.addActionListener(e -> fetchEmployeeDetails());
        btnUpdateEmployee.addActionListener(e -> updateEmployeeDetails());

        // Add action listeners to enable/disable fields based on checkboxes
        chkName.addItemListener(e -> nameField.setEnabled(chkName.isSelected()));
        chkSalary.addItemListener(e -> salaryField.setEnabled(chkSalary.isSelected()));
        chkEmail.addItemListener(e -> emailField.setEnabled(chkEmail.isSelected()));
        chkPhone.addItemListener(e -> phoneField.setEnabled(chkPhone.isSelected()));
    }

    // Method to fetch employee details based on ID
    private void fetchEmployeeDetails() {
        String empId = idField.getText();
        if (empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
            return;
        }

        String dbUrl = "jdbc:mysql://localhost:3306/EmployeeDB";
        String user = "root";
        String pass = "vicky";  // Your MySQL password
        String query = "SELECT id, name, salary, email, phone FROM Employee WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(empId));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Populate the fields with current employee data
                nameField.setText(rs.getString("name"));
                salaryField.setText(String.valueOf(rs.getDouble("salary")));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone"));

                // Enable the input fields if the user selects the corresponding checkbox
                chkName.setEnabled(true);
                chkSalary.setEnabled(true);
                chkEmail.setEnabled(true);
                chkPhone.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found with ID: " + empId);
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee details.");
        }
    }

    // Method to update employee details in the database
    private void updateEmployeeDetails() {
        String empId = idField.getText();
        if (empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
            return;
        }

        String dbUrl = "jdbc:mysql://localhost:3306/EmployeeDB";
        String user = "root";
        String pass = "vicky";  // Your MySQL password
        StringBuilder query = new StringBuilder("UPDATE Employee SET ");

        // Check which fields to update and build the query dynamically
        boolean isFirst = true;
        if (chkName.isSelected()) {
            if (!isFirst) query.append(", ");
            query.append("name = ?");
            isFirst = false;
        }
        if (chkSalary.isSelected()) {
            if (!isFirst) query.append(", ");
            query.append("salary = ?");
            isFirst = false;
        }
        if (chkEmail.isSelected()) {
            if (!isFirst) query.append(", ");
            query.append("email = ?");
            isFirst = false;
        }
        if (chkPhone.isSelected()) {
            if (!isFirst) query.append(", ");
            query.append("phone = ?");
        }
        query.append(" WHERE id = ?");

        try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int index = 1;
            if (chkName.isSelected()) stmt.setString(index++, nameField.getText());
            if (chkSalary.isSelected()) stmt.setDouble(index++, Double.parseDouble(salaryField.getText()));
            if (chkEmail.isSelected()) stmt.setString(index++, emailField.getText());
            if (chkPhone.isSelected()) stmt.setString(index++, phoneField.getText());
            stmt.setInt(index, Integer.parseInt(empId));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Employee details updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Error updating employee details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred.");
        }
    }

    // Method to clear fields after fetching data
    private void clearFields() {
        nameField.setText("");
        salaryField.setText("");
        emailField.setText("");
        phoneField.setText("");
        chkName.setEnabled(false);
        chkSalary.setEnabled(false);
        chkEmail.setEnabled(false);
        chkPhone.setEnabled(false);
    }
}
