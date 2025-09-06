package com.reliaquest.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.dtos.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Please <b>do not</b> modify this interface. If you believe there's a bug or the API contract does not align with our
 * mock web server... that is intentional. Good luck!
 *
 * @implNote It's uncommon to have a web controller implement an interface; We include such design pattern to
 * ensure users are following the desired input/output for our API contract, as outlined in the code assessment's README.
 *
 * @param <Entity> object representation of an Employee
 * @param <Input> object representation of a request body for creating Employee(s)
 */
public interface IEmployeeController<Entity, Input> {

    @GetMapping()
    ResponseEntity<List<Employee>> getAllEmployees() throws IOException;

    @GetMapping(ApiConstants.REST_API_URI_GET_EMPLOYEES_BY_NAME)
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping(ApiConstants.REST_API_URI_EMPLOYEE_ID)
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id);

    @GetMapping(ApiConstants.REST_API_URI_GET_HIGHEST_SALARY)
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping(ApiConstants.REST_API_URI_GET_TOP_TEN_EMPLOYEE_NAMES)
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<Object> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping(ApiConstants.REST_API_URI_EMPLOYEE_ID)
    ResponseEntity<Object> deleteEmployeeById(@PathVariable String id);
}
