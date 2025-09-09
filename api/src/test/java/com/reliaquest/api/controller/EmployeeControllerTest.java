package com.reliaquest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllEmployees_Success() throws IOException {
        // Arrange
        List<Employee> mockEmployees = createMockEmployeeList();
        when(employeeService.getAllEmployeeList()).thenReturn(mockEmployees);

        // Act
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEmployees, response.getBody());
        assertEquals(2, response.getBody().size());
        verify(employeeService, times(1)).getAllEmployeeList();
    }

    @Test
    void getAllEmployees_ThrowsException() {
        // Arrange
        when(employeeService.getAllEmployeeList()).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            employeeController.getAllEmployees();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Service error", exception.getReason());
        verify(employeeService, times(1)).getAllEmployeeList();
    }

    @Test
    void getEmployeesByNameSearch_Success() {
        // Arrange
        String searchString = "John";
        List<Employee> mockEmployees = createMockEmployeeList();
        when(employeeService.getEmployeeBySearchName(searchString)).thenReturn(mockEmployees);

        // Act
        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch(searchString);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEmployees, response.getBody());
        verify(employeeService, times(1)).getEmployeeBySearchName(searchString);
    }

    @Test
    void getEmployeesByNameSearch_ThrowsException() {
        // Arrange
        String searchString = "John";
        when(employeeService.getEmployeeBySearchName(searchString))
                .thenThrow(new RuntimeException("Search error"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            employeeController.getEmployeesByNameSearch(searchString);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Search error", exception.getReason());
        verify(employeeService, times(1)).getEmployeeBySearchName(searchString);
    }

    @Test
    void getEmployeeById_Success() {
        // Arrange
        String employeeId = "123";
        Employee mockEmployee = createMockEmployee();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(mockEmployee);

        // Act
        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEmployee, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void getEmployeeById_ThrowsException() {
        // Arrange
        String employeeId = "123";
        when(employeeService.getEmployeeById(employeeId))
                .thenThrow(new RuntimeException("Employee not found"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            employeeController.getEmployeeById(employeeId);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Employee not found", exception.getReason());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void getHighestSalaryOfEmployees_Success() {
        // Arrange
        Integer maxSalary = 100000;
        when(employeeService.getHighestSalaryOfEmployee()).thenReturn(maxSalary);

        // Act
        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(maxSalary, response.getBody());
        verify(employeeService, times(1)).getHighestSalaryOfEmployee();
    }

    @Test
    void getHighestSalaryOfEmployees_ThrowsException() {
        // Arrange
        when(employeeService.getHighestSalaryOfEmployee())
                .thenThrow(new RuntimeException("Salary calculation error"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            employeeController.getHighestSalaryOfEmployees();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Salary calculation error", exception.getReason());
        verify(employeeService, times(1)).getHighestSalaryOfEmployee();
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_Success() {
        // Arrange
        List<String> topTenNames = Arrays.asList("John Doe", "Jane Smith", "Bob Johnson");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(topTenNames);

        // Act
        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topTenNames, response.getBody());
        verify(employeeService, times(1)).getTopTenHighestEarningEmployeeNames();
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_ThrowsException() {
        // Arrange
        when(employeeService.getTopTenHighestEarningEmployeeNames())
                .thenThrow(new RuntimeException("Top ten calculation error"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            employeeController.getTopTenHighestEarningEmployeeNames();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Top ten calculation error", exception.getReason());
        verify(employeeService, times(1)).getTopTenHighestEarningEmployeeNames();
    }

    @Test
    void createEmployee_Success() {
        // Arrange
        Map<String, Object> employeeInput = createEmployeeInputMap();
        Object mockResponse = new HashMap<String, String>() {{
            put("status", "success");
            put("message", "Employee created successfully");
        }};
        when(employeeService.createEmployee(employeeInput)).thenReturn(mockResponse);

        // Act
        ResponseEntity<Object> response = employeeController.createEmployee(employeeInput);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(employeeService, times(1)).createEmployee(employeeInput);
    }

    @Test
    void createEmployee_ThrowsException() {
        // Arrange
        Map<String, Object> employeeInput = createEmployeeInputMap();
        when(employeeService.createEmployee(employeeInput))
                .thenThrow(new RuntimeException("Creation failed"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            employeeController.createEmployee(employeeInput);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Creation failed", exception.getReason());
        verify(employeeService, times(1)).createEmployee(employeeInput);
    }

    @Test
    void deleteEmployeeById_Success() {
        // Arrange
        String employeeId = "123";
        Object mockResponse = new HashMap<String, String>() {{
            put("status", "success");
            put("message", "Employee deleted successfully");
        }};
        when(employeeService.deleteEmployee(employeeId)).thenReturn(mockResponse);

        // Act
        ResponseEntity<Object> response = employeeController.deleteEmployeeById(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void deleteEmployeeById_ThrowsException() {
        // Arrange
        String employeeId = "123";
        when(employeeService.deleteEmployee(employeeId))
                .thenThrow(new RuntimeException("Deletion failed"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            employeeController.deleteEmployeeById(employeeId);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Deletion failed", exception.getReason());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    // Helper methods
    private Employee createMockEmployee() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployeeName("John Doe");
        employee.setEmployeeSalary(75000);
        employee.setEmployeeAge(30);
        employee.setEmployeeEmail("example@gmail.com");
        return employee;
    }

    private List<Employee> createMockEmployeeList() {
        Employee emp1 = createMockEmployee();

        Employee emp2 = new Employee();
        emp2.setId("2");
        emp2.setEmployeeName("Jane Smith");
        emp2.setEmployeeSalary(80000);
        emp2.setEmployeeAge(28);
        emp2.setEmployeeEmail("example@gmail.com");

        return Arrays.asList(emp1, emp2);
    }

    private Map<String, Object> createEmployeeInputMap() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "75000");
        employeeInput.put("age", "30");
        return employeeInput;
    }
}