import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewEmployee extends JFrame {

    private JTextArea textArea;

    public ViewEmployee() {
        // Set up the frame
        setTitle("Employee Details");
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Heading label
        JLabel lblHeading = new JLabel("Employee Details");
        lblHeading.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeading.setBounds(180, 30, 200, 30);
        getContentPane().add(lblHeading);

        // Create a text area to display employee details
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 80, 420, 200);
        getContentPane().add(scrollPane);

        // Fetch employee data from the database and display
        fetchEmployeeDetails();
    }

    // Method to fetch employee details from the database
    private void fetchEmployeeDetails() {
        String dbUrl = "jdbc:mysql://localhost:3306/EmployeeDB";
        String user = "root";
        String pass = "vicky";  // Your MySQL password
        String query = "SELECT id, name, salary FROM Employee";

        try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StringBuilder employeeDetails = new StringBuilder();
            employeeDetails.append("ID\tName\tSalary\n");
            employeeDetails.append("------------------------------------\n");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                employeeDetails.append(id).append("\t").append(name).append("\t").append(salary).append("\n");
            }

            textArea.setText(employeeDetails.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
