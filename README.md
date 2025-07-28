## Project Description
The Employee Management System provides an interface to add, view, update, and delete employee records. It uses **JDBC** for connecting and interacting with the database and **Swing** for building the graphical user interface (GUI) in Eclipse through drag-and-drop.

The system allows the user to:
- Add new employee details to the database.
- View all employees in the database.
- Update existing employee details.
- Delete employee records.

To run the Employee Management System , follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/Vikas4975/Employee_Management_System.git

The project uses JDBC to connect to a database.
Create a database (e.g., employee_db) and a table with the following structure:

CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    position VARCHAR(50),
    salary DECIMAL(10, 2)
);   
