package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void displayEmployeeTestNull()
    {
        app.displayEmployee(null);
    }

    @Test
    void displayEmployeeTestNotNull()
    {
        Employee e = new Employee();
        e.emp_no = 12345;
        e.first_name = "boyo";
        e.last_name = "jones";
        e.title = "idiot";
        e.salary = 10;
        e.dept_name = "being dumb";
        e.manager = "some jackass";
        app.displayEmployee(e);
    }
}