package com.reliaquest.api.constants;

public class ApiConstants {

	public static final String REST_API_URI_GET_EMPLOYEES_BY_NAME ="/search/{searchString}";
	public static final String REST_API_URI_EMPLOYEE_ID ="/{id}"; 
	public static final String REST_API_URI_GET_HIGHEST_SALARY="/highestSalary";
	public static final String REST_API_URI_GET_TOP_TEN_EMPLOYEE_NAMES="/topTenHighestEarningEmployeeNames";

	
	public static final String REST_API_URI_GET_ALL_EMPLOYEES="/employee";
	public static final String REST_API_URI_GET_EMPLOYEE_BY_ID="/employee/";
	public static final String REST_API_URI_CREATE_EMPLOYEE="/employee";
	public static final String REST_API_URI_DELETE_EMPLOYEE= "/employee";
	
	public static final String REST_API_BASE_URL = "http://localhost:8112/api/v1";
}
