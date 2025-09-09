package com.reliaquest.api.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Employee implements Comparable<Employee>{

	private String id;
	@NotBlank(message = "Name is mandatory")
	private String employeeName;
	@NotNull(message = "Salary is mandatory")
	@Min(value = 1, message = "Salary must be greater than 0")
	private Integer employeeSalary;

	@NotNull(message = "Age is mandatory")
	@Min(value = 16, message = "Age must be at least 16")
	@Max(value = 75, message = "Age must be less than or equal to 75")
	private Integer employeeAge;
	@NotBlank(message = "Title is mandatory")
	private String employeeTitle;
	private String employeeEmail;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Integer getEmployeeSalary() {
		return employeeSalary;
	}
	public void setEmployeeSalary(Integer employeeSalary) {
		this.employeeSalary = employeeSalary;
	}
	public Integer getEmployeeAge() {
		return employeeAge;
	}
	public void setEmployeeAge(Integer employeeAge) {
		this.employeeAge = employeeAge;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeeTitle() {
		return employeeTitle;
	}

	public void setEmployeeTitle(String employeeTitle) {
		this.employeeTitle = employeeTitle;
	}
	@Override
	public int compareTo(Employee o) {
		return Integer.compare(o.employeeSalary, this.employeeSalary);
		//return Integer.parseInt(String.valueOf(o.employeeSalary))-Integer.parseInt(String.valueOf(this.employeeSalary));
	}


}
