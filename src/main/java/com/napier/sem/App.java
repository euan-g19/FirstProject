package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        /*      UNIT 2-3
        // Get Employee
        Employee emp = a.getEmployee(255530);
        // Display results
        a.displayEmployee(emp);
        */

        a.salaryByRoleReport("Engineer");


        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Employee getEmployee(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, titles.title, salaries.salary, departments.dept_no, departments.dept_name, temp.first_name AS man_first, temp.last_name AS man_last "
                            + "FROM employees, titles, salaries, dept_emp, departments, "
                            + "(SELECT employees.first_name, employees.last_name, dept_manager.dept_no, dept_manager.emp_no "
                            + "FROM employees JOIN dept_manager ON (employees.emp_no = dept_manager.emp_no)) AS temp "
                            + "WHERE employees.emp_no = " + ID +
                                " AND titles.emp_no = employees.emp_no" +
                                " AND salaries.emp_no = employees.emp_no" +
                                " AND departments.dept_no = (SELECT dept_no FROM dept_emp WHERE emp_no = " + ID + ")" +
                                " AND temp.dept_no = (SELECT dept_no FROM dept_emp WHERE emp_no = " + ID + ")";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);


            // Return new employee if valid.
            // Check one is returned
            if (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                emp.dept_name = rset.getString("dept_name");
                emp.manager = rset.getString("man_first") + " " + rset.getString("man_last");
                return emp;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + "Title: " + emp.title + "\n"
                            + "Salary: £" + emp.salary + "\n"
                            + "Department: " + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    public void salaryByRoleReport(String titleIn)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary\n" +
                    "FROM employees, salaries, titles\n" +
                    "WHERE employees.emp_no = salaries.emp_no\n" +
                    "AND employees.emp_no = titles.emp_no\n" +
                    "AND salaries.to_date = '9999-01-01'\n" +
                    "AND titles.to_date = '9999-01-01'\n" +
                    "AND titles.title = \"" + titleIn + "\"\n" +
                    "ORDER BY employees.emp_no";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next())
            {
                System.out.println("ID: " + rset.getInt("emp_no") + "  |  "
                                + "Name: " + rset.getString("first_name") + " " + rset.getString("last_name") + "  |  "
                                + "Salary: £" + rset.getInt("salary"));
            }

        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            System.out.println("Failed to get employee details");
        }

    }

}