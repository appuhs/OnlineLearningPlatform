import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployee extends JFrame {

    private JTextField nameField, salaryField, emailField, phoneField;
    
    public AddEmployee() {
        // Set up the frame
        setTitle("Add New Employee");
        setBounds(100, 100, 400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Heading label
        JLabel lblHeading = new JLabel("Add New Employee");
        lblHeading.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeading.setBounds(120, 30, 200, 30);
        getContentPane().add(lblHeading);

        // Name label and text field
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 80, 100, 25);
        getContentPane().add(lblName);

        nameField = new JTextField();
        nameField.setBounds(140, 80, 200, 25);
        getContentPane().add(nameField);

        // Salary label and text field
        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setBounds(30, 120, 100, 25);
        getContentPane().add(lblSalary);

        salaryField = new JTextField();
        salaryField.setBounds(140, 120, 200, 25);
        getContentPane().add(salaryField);

        // Email label and text field
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 160, 100, 25);
        getContentPane().add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(140, 160, 200, 25);
        getContentPane().add(emailField);

        // Phone label and text field
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(30, 200, 100, 25);
        getContentPane().add(lblPhone);

        phoneField = new JTextField();
        phoneField.setBounds(140, 200, 200, 25);
        getContentPane().add(phoneField);

        // Button to add employee
        JButton btnAddEmployee = new JButton("Add Employee");
        btnAddEmployee.setBounds(140, 240, 150, 30);
        getContentPane().add(btnAddEmployee);

        // Button action listener
        btnAddEmployee.addActionListener(e -> addEmployeeToDatabase());
    }

    // Method to add employee to the database
    private void addEmployeeToDatabase() {
        String name = nameField.getText();
        String salary = salaryField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        // Validate the input
        if (name.isEmpty() || salary.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            // Convert salary to double
            double salaryValue = Double.parseDouble(salary);

            // Prepare SQL query
            String dbUrl = "jdbc:mysql://localhost:3306/EmployeeDB";
            String user = "root";
            String pass = "vicky	";  // Your MySQL password

            String query = "INSERT INTO Employee (name, salary, email, phone) VALUES (?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, name);
                stmt.setDouble(2, salaryValue);
                stmt.setString(3, email);
                stmt.setString(4, phone);

                // Execute the query to insert the employee data
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee added successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding employee.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid salary.");
        }
    }

    // Method to clear the fields after adding employee
    private void clearFields() {
        nameField.setText("");
        salaryField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}
