package com.reliaquest.api.util;

import com.reliaquest.api.dtos.Employee;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for creating test data across different test classes
 */
public class TestDataUtil {

    public static Employee createMockEmployee() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployeeName("John Doe");
        employee.setEmployeeSalary(75000);
        employee.setEmployeeAge(30);
        employee.setEmployeeEmail("example@gmail.com");
        return employee;
    }

    public static Employee createMockEmployee(String id, String name, Integer salary, Integer age) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEmployeeName(name);
        employee.setEmployeeSalary(salary);
        employee.setEmployeeAge(age);
        employee.setEmployeeEmail("example@gmail.com");
        return employee;
    }

    public static List<Employee> createMockEmployeeList() {
        Employee emp1 = createMockEmployee();

        Employee emp2 = new Employee();
        emp2.setId("2");
        emp2.setEmployeeName("Jane Smith");
        emp2.setEmployeeSalary(80000);
        emp2.setEmployeeAge(28);
        emp2.setEmployeeEmail("example@gmail.com");

        return Arrays.asList(emp1, emp2);
    }

    public static List<Employee> createMockEmployeeListWithMultipleEmployees() {
        return Arrays.asList(
                createMockEmployee("1", "John Doe", 75000, 30),
                createMockEmployee("2", "Jane Smith", 80000, 28),
                createMockEmployee("3", "Bob Johnson", 90000, 35),
                createMockEmployee("4", "Alice Brown", 95000, 32),
                createMockEmployee("5", "Charlie Wilson", 85000, 29)
        );
    }

    public static Map<String, Object> createEmployeeInputMap() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", 75000);
        employeeInput.put("age", 30);
        employeeInput.put("title", "Software Engineer");
        return employeeInput;
    }

    public static Map<String, Object> createEmployeeInputMap(String name, String salary, String age) {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", name);
        employeeInput.put("salary", salary);
        employeeInput.put("age", age);
        return employeeInput;
    }
}