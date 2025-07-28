import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewEmployeeById extends JFrame {

    private JTextField idField;
    private JCheckBox checkBoxID, checkBoxName, checkBoxSalary, checkBoxEmail, checkBoxPhone;
    private JTextArea textArea;
    
    public ViewEmployeeById() {
        // Set up the frame
        setTitle("Employee Details by ID");
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Heading label
        JLabel lblHeading = new JLabel("Enter Employee ID to View Details");
        lblHeading.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeading.setBounds(120, 30, 300, 30);
        getContentPane().add(lblHeading);

        // Label for ID input
        JLabel lblEnterID = new JLabel("Employee ID:");
        lblEnterID.setBounds(30, 80, 100, 25);
        getContentPane().add(lblEnterID);

        // Text field for Employee ID
        idField = new JTextField();
        idField.setBounds(140, 80, 150, 25);
        getContentPane().add(idField);

        // Checkboxes for displaying employee details
        checkBoxID = new JCheckBox("ID");
        checkBoxID.setBounds(30, 120, 60, 25);
        getContentPane().add(checkBoxID);

        checkBoxName = new JCheckBox("Name");
        checkBoxName.setBounds(100, 120, 80, 25);
        getContentPane().add(checkBoxName);

        checkBoxSalary = new JCheckBox("Salary");
        checkBoxSalary.setBounds(180, 120, 80, 25);
        getContentPane().add(checkBoxSalary);

        checkBoxEmail = new JCheckBox("Email");
        checkBoxEmail.setBounds(260, 120, 80, 25);
        getContentPane().add(checkBoxEmail);

        checkBoxPhone = new JCheckBox("Phone");
        checkBoxPhone.setBounds(340, 120, 80, 25);
        getContentPane().add(checkBoxPhone);

        // Button to show employee details
        JButton btnShowDetails = new JButton("Show Details");
        btnShowDetails.setBounds(150, 160, 150, 30);
        getContentPane().add(btnShowDetails);

        // Text area to display employee details
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 200, 420, 100);
        getContentPane().add(scrollPane);

        // Button action
        btnShowDetails.addActionListener(e -> fetchEmployeeDetails());
    }

    // Method to fetch employee details based on ID and selected checkboxes
    private void fetchEmployeeDetails() {
        String empId = idField.getText();
        if (empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID");
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

                // Display selected details based on checkboxes
                if (checkBoxID.isSelected()) {
                    employeeDetails.append("ID: ").append(rs.getInt("id")).append("\n");
                }
                if (checkBoxName.isSelected()) {
                    employeeDetails.append("Name: ").append(rs.getString("name")).append("\n");
                }
                if (checkBoxSalary.isSelected()) {
                    employeeDetails.append("Salary: ").append(rs.getDouble("salary")).append("\n");
                }
                if (checkBoxEmail.isSelected()) {
                    employeeDetails.append("Email: ").append(rs.getString("email")).append("\n");
                }
                if (checkBoxPhone.isSelected()) {
                    employeeDetails.append("Phone: ").append(rs.getString("phone")).append("\n");
                }

                // Show the employee details in the text area
                if (employeeDetails.length() > 0) {
                    textArea.setText(employeeDetails.toString());
                } else {
                    textArea.setText("Please select at least one detail to view.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found with ID: " + empId);
                textArea.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee details.");
        }
    }
}
