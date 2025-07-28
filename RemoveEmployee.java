

	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
	import java.sql.*;

	public class RemoveEmployee extends JFrame {

	    private JTextField idField;
	    private JTextArea textArea;
	    
	    public RemoveEmployee() {
	        // Set up the frame
	        setTitle("Remove Employee Details");
	        setBounds(100, 100, 400, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        getContentPane().setLayout(null);

	        // Heading label
	        JLabel lblHeading = new JLabel("Remove Employee Details");
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

	        // Text area to display employee details
	        textArea = new JTextArea();
	        textArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(textArea);
	        scrollPane.setBounds(30, 160, 320, 100);
	        getContentPane().add(scrollPane);

	        // Button to remove employee
	        JButton btnRemoveEmployee = new JButton("Remove Employee");
	        btnRemoveEmployee.setBounds(150, 280, 150, 30);
	        getContentPane().add(btnRemoveEmployee);

	        // Button actions
	        btnFetchDetails.addActionListener(e -> fetchEmployeeDetails());
	        btnRemoveEmployee.addActionListener(e -> removeEmployeeFromDatabase());
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
	                StringBuilder employeeDetails = new StringBuilder();
	                employeeDetails.append("ID: ").append(rs.getInt("id")).append("\n")
	                               .append("Name: ").append(rs.getString("name")).append("\n")
	                               .append("Salary: ").append(rs.getDouble("salary")).append("\n")
	                               .append("Email: ").append(rs.getString("email")).append("\n")
	                               .append("Phone: ").append(rs.getString("phone")).append("\n");

	                // Show the employee details in the text area
	                textArea.setText(employeeDetails.toString());
	            } else {
	                JOptionPane.showMessageDialog(this, "Employee not found with ID: " + empId);
	                textArea.setText("");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error fetching employee details.");
	        }
	    }

	    // Method to remove employee from the database
	    private void removeEmployeeFromDatabase() {
	        String empId = idField.getText();
	        if (empId.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
	            return;
	        }

	        // Confirm deletion
	        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", 
	                                                     "Confirm Deletion", JOptionPane.YES_NO_OPTION);

	        if (confirm == JOptionPane.YES_OPTION) {
	            String dbUrl = "jdbc:mysql://localhost:3306/EmployeeDB";
	            String user = "root";
	            String pass = "vicky";  // Your MySQL password
	            String query = "DELETE FROM Employee WHERE id = ?";

	            try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
	                 PreparedStatement stmt = conn.prepareStatement(query)) {

	                stmt.setInt(1, Integer.parseInt(empId));

	                // Execute the delete query
	                int rowsAffected = stmt.executeUpdate();

	                if (rowsAffected > 0) {
	                    JOptionPane.showMessageDialog(this, "Employee removed successfully.");
	                    textArea.setText("");  // Clear text area after removal
	                } else {
	                    JOptionPane.showMessageDialog(this, "Error removing employee or employee not found.");
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Database error occurred.");
	            }
	        }
	    }
	}


