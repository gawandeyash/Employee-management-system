package com.reliaquest.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reliaquest.api.controller.IEmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reliaquest.api.dtos.Employee;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import com.reliaquest.api.controller.IEmployeeController;

@RestController
public class EmployeeController implements IEmployeeController {

	@Autowired
	private EmployeeServiceImpl employeeService;

	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
		logger.debug("EmployeeController|getAllEmployees|Entry");
		List<Employee> employeeList = new ArrayList<>();
		try {
			employeeList = employeeService.getAllEmployeeList();
		} catch (Exception e) {
			logger.error("EmployeeController|getAllEmployees|Error:{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		logger.debug("EmployeeController|getAllEmployees|Exit");

		return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {

		logger.debug("EmployeeController|getEmployeesByNameSearch|Entry");
		List<Employee> employeeListByName = new ArrayList<>();
		try {
			employeeListByName = employeeService.getEmployeeBySearchName(searchString);
		} catch (Exception e) {
			logger.error("EmployeeController|getEmployeesByNameSearch|Error:{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		logger.debug("EmployeeController|getEmployeesByNameSearch|Exit");

		return new ResponseEntity<List<Employee>>(employeeListByName, HttpStatus.OK);
	}

	//http://localhost:8111/ed23ec1c-5647-4a3d-8599-632dba543d00
	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		logger.debug("EmployeeController|getEmployeeById|Entry");

		Employee employee = new Employee();
		try {
			employee = employeeService.getEmployeeById(id);
			if (employee == null) {
				logger.warn("EmployeeController|getEmployeeById|Employee not found for id: {}", id);
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
			}
		} catch (Exception e) {
			logger.error("EmployeeController|getEmployeeById|Error:{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

		logger.debug("EmployeeController|getEmployeeById|Exit");

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	//http://localhost:8111/highestSalary
	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		logger.debug("EmployeeController|getHighestSalaryOfEmployees|Entry");

		Integer maxSalary = null;
		try {
			maxSalary = employeeService.getHighestSalaryOfEmployee();

		} catch (Exception e) {
			logger.error("EmployeeController|getHighestSalaryOfEmployees|Error:{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		logger.debug("EmployeeController|getHighestSalaryOfEmployees|Exit");

		return new ResponseEntity<Integer>(maxSalary, HttpStatus.OK);
	}

	//http://localhost:8111/topTenHighestEarningEmployeeNames
	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		logger.debug("EmployeeController|getTopTenHighestEarningEmployeeNames|Entry");

		List<String> topTenHighestEarningEmpNamesList = new ArrayList<>();
		try {
			topTenHighestEarningEmpNamesList = employeeService.getTopTenHighestEarningEmployeeNames();

		} catch (Exception e) {
			logger.error("EmployeeController|getTopTenHighestEarningEmployeeNames|Error:{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

		logger.debug("EmployeeController|getTopTenHighestEarningEmployeeNames|Exit");

		return new ResponseEntity<List<String>>(topTenHighestEarningEmpNamesList, HttpStatus.OK);
	}

//	{
//		"name": "Yash",
//			"salary": 185000,
//			"age": 25,
//			"title": "Software Developer"
//	}
	@Override
	public ResponseEntity<Object> createEmployee(Map employeeInput) {
		logger.debug("EmployeeController|createEmployee|Entry");

		if (employeeInput == null || employeeInput.isEmpty() ) {
			logger.error("EmployeeController|createEmployee|Invalid input");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee input");
		}

		String name = (String) employeeInput.get("name");
		Integer salary = (Integer) employeeInput.get("salary");
		Integer age = (Integer) employeeInput.get("age");
		String title = (String) employeeInput.get("title");

		if (name == null || name.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is mandatory");
		}
		if (salary == null || salary < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salary must be greater than 0");
		}
		if (age == null || age < 16 || age > 75) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Age must be between 16 and 75");
		}
		if (title == null || title.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is mandatory");
		}

		Object response = null;
		try {
			response = employeeService.createEmployee(employeeInput);
		} catch (Exception e) {
			logger.error("EmployeeController|createEmployee|Error:{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		logger.debug("EmployeeController|createEmployee|Employee Created Successfully|Exit");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteEmployeeById(String id) {
		logger.debug("EmployeeController|deleteEmployeeById|Entry");

		Object response = null;
		try {
			response = employeeService.deleteEmployee(id);

		}
		catch (ResponseStatusException e) {
			logger.error("EmployeeController|deleteEmployeeById|Handled error: {}", e.getReason());
			throw e;
		}
		catch (Exception e) {
			logger.error("EmployeeController|deleteEmployeeById|Unexpected error", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		logger.debug("EmployeeController|deleteEmployeeById|Employee Deleted Successfully|Exit");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
