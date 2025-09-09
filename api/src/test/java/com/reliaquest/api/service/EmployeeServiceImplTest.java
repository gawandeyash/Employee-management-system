package com.reliaquest.api.service;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.dtos.DeleteEmployeeByNameDTO;
import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.dtos.EmployeeByIdResponseDTO;
import com.reliaquest.api.dtos.EmployeeListResponseDTO;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import com.reliaquest.api.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.reliaquest.api.util.TestDataUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    //private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.RequestBodyUriSpec deleteRequestBodyUriSpec;

    @Mock private WebClient.RequestHeadersSpec deleteRequestHeadersSpec;


    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(webClient, requestHeadersUriSpec, requestHeadersSpec, requestBodyUriSpec, requestBodySpec, responseSpec);
    }

    @Test
    void getAllEmployeeList_Success() {
        // Arrange
        List<Employee> mockEmployees = createMockEmployeeList();
        EmployeeListResponseDTO responseDTO = new EmployeeListResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(mockEmployees);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_ALL_EMPLOYEES)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeListResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        // Act
        List<Employee> result = employeeService.getAllEmployeeList();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeName());
        assertEquals("Jane Smith", result.get(1).getEmployeeName());
        verify(webClient).get();
    }

    @Test
    void getEmployeeById_Success() {
        // Arrange
        String employeeId = "123";
        Employee mockEmployee = createMockEmployee();
        EmployeeByIdResponseDTO responseDTO = new EmployeeByIdResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(mockEmployee);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_EMPLOYEE_BY_ID + employeeId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeByIdResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        // Act
        Employee result = employeeService.getEmployeeById(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getEmployeeName());
        assertEquals("75000", result.getEmployeeSalary());
        verify(webClient).get();
    }

    @Test
    void getEmployeeBySearchName_Success() {
        // Arrange
        String searchString = "john";
        List<Employee> mockEmployees = createMockEmployeeList();
        EmployeeListResponseDTO responseDTO = new EmployeeListResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(mockEmployees);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_ALL_EMPLOYEES)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeListResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        // Act
        List<Employee> result = employeeService.getEmployeeBySearchName(searchString);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeName());
        verify(webClient).get();
    }

    @Test
    void getEmployeeBySearchName_CaseInsensitive() {
        // Arrange
        String searchString = "JANE";
        List<Employee> mockEmployees = createMockEmployeeList();
        EmployeeListResponseDTO responseDTO = new EmployeeListResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(mockEmployees);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_ALL_EMPLOYEES)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeListResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        // Act
        List<Employee> result = employeeService.getEmployeeBySearchName(searchString);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jane Smith", result.get(0).getEmployeeName());
    }

    @Test
    void getEmployeeBySearchName_NoMatch() {
        // Arrange
        String searchString = "nonexistent";
        List<Employee> mockEmployees = createMockEmployeeList();
        EmployeeListResponseDTO responseDTO = new EmployeeListResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(mockEmployees);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_ALL_EMPLOYEES)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeListResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        // Act
        List<Employee> result = employeeService.getEmployeeBySearchName(searchString);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getHighestSalaryOfEmployee_Success() {
        // Arrange
        List<Employee> mockEmployees = createMockEmployeeList();
        EmployeeListResponseDTO responseDTO = new EmployeeListResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(mockEmployees);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_ALL_EMPLOYEES)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeListResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        // Act
        Integer result = employeeService.getHighestSalaryOfEmployee();

        // Assert
        assertNotNull(result);
        assertEquals(80000, result);
        verify(webClient).get();
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_Success() {
        // Arrange
        List<Employee> mockEmployees = createMockEmployeeListWithMultipleEmployees();
        EmployeeListResponseDTO responseDTO = new EmployeeListResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setData(mockEmployees);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_ALL_EMPLOYEES)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeListResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        // Act
        List<String> result = employeeService.getTopTenHighestEarningEmployeeNames();

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= 10);
        assertEquals("Bob Johnson", result.get(0)); // Highest salary
        assertEquals("Jane Smith", result.get(1));  // Second highest
        verify(webClient).get();
    }

    @Test
    void createEmployee_Success() {
        // Arrange
        Map<String, Object> employeeInput = createEmployeeInputMap();
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("status", "success");
        mockResponse.put("message", "Employee created successfully");

        // Mock the entire chain
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(BodyInserter.class))).thenReturn(requestHeadersSpec); // <-- fix
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(Class.class))).thenReturn(Mono.just(mockResponse));


        // Act & Assert
        Object result = employeeService.createEmployee(employeeInput);
        assertEquals(mockResponse, result);
    }

    @Test
    void deleteEmployee_Success() {
        // Arrange
        String employeeId = "123";
        Employee mockEmployee = createMockEmployee();
        EmployeeByIdResponseDTO getResponseDTO = new EmployeeByIdResponseDTO();
        getResponseDTO.setStatus("success");
        getResponseDTO.setData(mockEmployee);

        Map<String, Object> deleteResponse = new HashMap<>();
        deleteResponse.put("status", "success");
        deleteResponse.put("message", "Employee deleted successfully");

        // Mock GET request for employee by ID
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_EMPLOYEE_BY_ID + employeeId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeByIdResponseDTO.class)).thenReturn(Mono.just(getResponseDTO));

        // Mock DELETE request
        WebClient.RequestBodySpec deleteRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec deleteResponseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.method(HttpMethod.DELETE)).thenReturn(deleteRequestBodyUriSpec);

        when(deleteRequestBodyUriSpec.uri(eq(ApiConstants.REST_API_URI_DELETE_EMPLOYEE)))
                .thenReturn(deleteRequestBodySpec);

        when(deleteRequestBodySpec.header(eq(HttpHeaders.CONTENT_TYPE), eq(MediaType.APPLICATION_JSON_VALUE)))
                .thenReturn(deleteRequestBodySpec);

        when(deleteRequestBodySpec.bodyValue(any(DeleteEmployeeByNameDTO.class)))
                .thenReturn(deleteRequestHeadersSpec); // bodyValue returns RequestHeadersSpec<?>

        when(deleteRequestHeadersSpec.retrieve()).thenReturn(deleteResponseSpec);

        when(deleteResponseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(deleteResponse));


        // Act
        Object result = employeeService.deleteEmployee(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(deleteResponse, result);
        verify(webClient).get(); // Verify GET call for employee details
        verify(webClient).method(HttpMethod.DELETE); // Verify DELETE call
    }

    @Test
    void deleteEmployee_EmployeeNotFound() {
        // Arrange
        String employeeId = "123";

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(ApiConstants.REST_API_URI_GET_EMPLOYEE_BY_ID + employeeId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EmployeeByIdResponseDTO.class)).thenReturn(Mono.just(new EmployeeByIdResponseDTO()));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.deleteEmployee(employeeId);
        });

        assertEquals("Employee not found with ID: " + employeeId, exception.getMessage());
        verify(webClient).get();
        verify(webClient, never()).method(HttpMethod.DELETE);
    }

}