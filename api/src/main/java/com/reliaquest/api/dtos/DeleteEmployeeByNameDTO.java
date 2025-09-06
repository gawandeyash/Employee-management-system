package com.reliaquest.api.dtos;

public class DeleteEmployeeByNameDTO {
    private String name;

    // Default constructor (required for JSON deserialization)
    public DeleteEmployeeByNameDTO() {}

    // Constructor with name
    public DeleteEmployeeByNameDTO(String name) {
        this.name = name;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DeleteEmployeeByNameDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
