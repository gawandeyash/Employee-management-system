package com.reliaquest.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.reliaquest.api.dtos.DeleteEmployeeByNameDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.dtos.EmployeeByIdResponseDTO;
import com.reliaquest.api.dtos.EmployeeListResponseDTO;
import com.reliaquest.api.service.EmployeeService;

import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	public WebClient webClient;

	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public List<Employee> getAllEmployeeList() {
		logger.info("EmployeeService|getAllEmployeeList|Entry");
		Mono<EmployeeListResponseDTO> employeeMono = webClient.get().uri(ApiConstants.REST_API_URI_GET_ALL_EMPLOYEES)
				.retrieve().bodyToMono(EmployeeListResponseDTO.class);

		logger.info("EmployeeService|getAllEmployeeList|Exit");
		return employeeMono.block().getData();
	}

	@Override
	public Employee getEmployeeById(String id) {
		logger.debug("EmployeeService|getEmployeeById|Entry");

		Mono<EmployeeByIdResponseDTO> employeeMono = webClient.get()
				.uri(ApiConstants.REST_API_URI_GET_EMPLOYEE_BY_ID + id).retrieve()
				.bodyToMono(EmployeeByIdResponseDTO.class);

		logger.debug("EmployeeService|getEmployeeById|Exit");

		return employeeMono.block().getData();
	}

	@Override
	public List<Employee> getEmployeeBySearchName(String searchString) {
		logger.debug("EmployeeService|getEmployeeBySearchName|Entry");

		List<Employee> employeeList = getAllEmployeeList();

		logger.debug("EmployeeService|getEmployeeBySearchName|Exit");

		return employeeList.stream().filter(emp -> emp.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public Integer getHighestSalaryOfEmployee() {
		logger.debug("EmployeeService|getHighestSalaryOfEmployee|Entry");

		List<Employee> employeeList = getAllEmployeeList();
		logger.debug("EmployeeService|getHighestSalaryOfEmployee|Exit");

		return employeeList.stream().map(emp -> emp.getEmployeeSalary()).mapToInt(Integer::parseInt).max().getAsInt();
	}

	@Override
	public List<String> getTopTenHighestEarningEmployeeNames() {
		logger.debug("EmployeeService|getTopTenHighestEarningEmployeeNames|Entry");

		List<Employee> employeeList = getAllEmployeeList();

		logger.debug("EmployeeService|getTopTenHighestEarningEmployeeNames|Exit");

		return employeeList.stream().sorted().map(emp -> emp.getEmployeeName()).limit(10).collect(Collectors.toList());
	}

	@Override
	public Object createEmployee(Map<String, Object> employeeInput) {
		logger.info("EmployeeService|createEmployee|Entry");

		Object employeeMono = webClient.post().uri(ApiConstants.REST_API_URI_CREATE_EMPLOYEE)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(employeeInput)).retrieve().bodyToMono(Map.class).block();

		logger.info("EmployeeService|createEmployee|Exit");

		return employeeMono;
	}

	@Override
	public Object deleteEmployee(String id) {
		logger.debug("EmployeeService|deleteEmployee|Entry");

		// Step 1: Get employee by ID to fetch their name
		Employee employee = getEmployeeById(id);

		if (employee == null) {
			logger.error("Employee not found with ID: " + id);
			throw new RuntimeException("Employee not found with ID: " + id);
		}

		// Step 2: Create DeleteEmployeeByNameDTO with the employee name
		DeleteEmployeeByNameDTO deleteInput = new DeleteEmployeeByNameDTO();
		deleteInput.setName(employee.getEmployeeName());

		// Step 3: Call delete API using method() since delete() doesn't support body
		Object employeeMono = webClient.method(HttpMethod.DELETE)
				.uri(ApiConstants.REST_API_URI_DELETE_EMPLOYEE)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.bodyValue(deleteInput) // Send DeleteEmployeeByNameDTO object
				.retrieve()
				.bodyToMono(Object.class)
				.block();

		logger.debug("EmployeeService|deleteEmployee|Exit");
		return employeeMono;
	}

	

}
