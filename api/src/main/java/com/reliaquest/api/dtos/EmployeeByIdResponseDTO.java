package com.reliaquest.api.dtos;

public class EmployeeByIdResponseDTO {
	private String status;
	private Employee data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getData() {
		return data;
	}

	public void setData(Employee data) {
		this.data = data;
	}
}
