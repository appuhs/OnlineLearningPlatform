import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeManagementSystem {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EmployeeManagementSystem window = new EmployeeManagementSystem();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public EmployeeManagementSystem() {
        initialize();
    }

    private void initialize() {
        // Create main frame
        frame = new JFrame("Employee Management System");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Heading label
        JLabel lblHeading = new JLabel("Welcome to Employee Management System");
        lblHeading.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeading.setBounds(80, 30, 350, 30);
        frame.getContentPane().add(lblHeading);

        // View Employee button
        JButton btnViewEmployee = new JButton("View Employees");
        btnViewEmployee.setBounds(150, 100, 200, 30);
        frame.getContentPane().add(btnViewEmployee);

        // View Employee by ID button
        JButton btnViewEmployeeById = new JButton("View Employee by ID");
        btnViewEmployeeById.setBounds(150, 140, 200, 30);
        frame.getContentPane().add(btnViewEmployeeById);

        // Add Employee button
        JButton btnAddEmployee = new JButton("Add Employee");
        btnAddEmployee.setBounds(150, 180, 200, 30);
        frame.getContentPane().add(btnAddEmployee);

        // Remove Employee button
        JButton btnRemoveEmployee = new JButton("Remove Employee");
        btnRemoveEmployee.setBounds(150, 220, 200, 30);
        frame.getContentPane().add(btnRemoveEmployee);

        // Update Employee button
        JButton btnUpdateEmployee = new JButton("Update Employee");
        btnUpdateEmployee.setBounds(150, 260, 200, 30);
        frame.getContentPane().add(btnUpdateEmployee);

        // Button actions
        btnViewEmployee.addActionListener(e -> openViewEmployeePage());
        btnViewEmployeeById.addActionListener(e -> openViewEmployeeByIdPage());
        btnAddEmployee.addActionListener(e -> openAddEmployeePage());
        btnRemoveEmployee.addActionListener(e -> openRemoveEmployeePage());
        btnUpdateEmployee.addActionListener(e -> openUpdateEmployeePage());
    }

    // Open the "View Employee" page
    private void openViewEmployeePage() {
        new ViewEmployee().setVisible(true);
        frame.dispose();  // Close the main window when new page is opened
    }

    // Open the "View Employee by ID" page
    private void openViewEmployeeByIdPage() {
        new ViewEmployeeById().setVisible(true);
        frame.dispose();
    }

   //  Open the "Add Employee" page
    private void openAddEmployeePage() {
        new AddEmployee().setVisible(true);
        frame.dispose();
    }
//
    // Open the "Remove Employee" page
    private void openRemoveEmployeePage() {
        new RemoveEmployee().setVisible(true);
        frame.dispose();
    }

    // Open the "Update Employee" page
    private void openUpdateEmployeePage() {
        new UpdateEmployee().setVisible(true);
        frame.dispose();
    }
}


	