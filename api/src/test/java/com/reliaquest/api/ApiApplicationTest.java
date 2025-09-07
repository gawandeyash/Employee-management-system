package com.reliaquest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import com.reliaquest.api.dtos.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.reliaquest.api.util.TestDataUtil.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployees_ReturnsEmployeeList() throws Exception {
        // Arrange
        List<Employee> mockEmployees = createMockEmployeeList();
        when(employeeService.getAllEmployeeList()).thenReturn(mockEmployees);

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].employee_name").value("John Doe"))
                .andExpect(jsonPath("$[1].employee_name").value("Jane Smith"));
    }

    @Test
    void getEmployeesByNameSearch_ReturnsFilteredEmployees() throws Exception {
        // Arrange
        List<Employee> mockEmployees = Arrays.asList(createMockEmployee());
        when(employeeService.getEmployeeBySearchName("John")).thenReturn(mockEmployees);

        // Act & Assert
        mockMvc.perform(get("/search/John"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].employee_name").value("John Doe"));
    }

    @Test
    void getEmployeeById_ReturnsEmployee() throws Exception {
        // Arrange
        Employee mockEmployee = createMockEmployee();
        when(employeeService.getEmployeeById("123")).thenReturn(mockEmployee);

        // Act & Assert
        mockMvc.perform(get("/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employee_name").value("John Doe"))
                .andExpect(jsonPath("$.employee_salary").value("75000"));
    }

    @Test
    void getHighestSalaryOfEmployees_ReturnsMaxSalary() throws Exception {
        // Arrange
        when(employeeService.getHighestSalaryOfEmployee()).thenReturn(100000);

        // Act & Assert
        mockMvc.perform(get("/highestSalary"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("100000"));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_ReturnsNamesList() throws Exception {
        // Arrange
        List<String> topNames = Arrays.asList("John Doe", "Jane Smith", "Bob Johnson");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(topNames);

        // Act & Assert
        mockMvc.perform(get("/topTenHighestEarningEmployeeNames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value("John Doe"))
                .andExpect(jsonPath("$[1]").value("Jane Smith"));
    }

    @Test
    void createEmployee_ReturnsSuccessResponse() throws Exception {
        // Arrange
        Map<String, Object> employeeInput = createEmployeeInputMap();
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("status", "success");
        mockResponse.put("message", "Employee created successfully");

        when(employeeService.createEmployee(any(Map.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeInput)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void deleteEmployeeById_ReturnsSuccessResponse() throws Exception {
        // Arrange
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("status", "success");
        mockResponse.put("message", "Employee deleted successfully");

        when(employeeService.deleteEmployee("123")).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(delete("/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void getEmployeeById_WhenNotFound_Returns404() throws Exception {
        when(employeeService.getEmployeeById("999"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        mockMvc.perform(get("/999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAllEmployees_WhenNoEmployees_ReturnsEmptyList() throws Exception {
        when(employeeService.getAllEmployeeList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void createEmployee_WhenInvalidInput_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // missing required fields
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteEmployeeById_WhenNotFound_Returns404() throws Exception {
        when(employeeService.deleteEmployee("999"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        mockMvc.perform(delete("/999"))
                .andExpect(status().isNotFound());
    }

}