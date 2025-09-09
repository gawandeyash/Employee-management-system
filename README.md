# ReliaQuest Coding Challenge

I have completed the Employee Management System assignment as per the requirements mentioned in this file.

Steps to run the application:
1. Run ApiApplication.java (localhost:8111)
2. Run ServerApplication.java (localhost:8112)
3. To run unit tests start ApiApplicationTest.java

Below are the API's with endpoints and the sample input 
1. getAllEmployees - http://localhost:8111/
2. getEmployeesByNameSearch - http://localhost:8111/{name}
3. getEmployeeById - GET http://localhost:8111/{id}
4. getHighestSalaryOfEmployees - GET http://localhost:8111/highestSalary
5. getTopTenHighestEarningEmployeeNames - GET http://localhost:8111/topTenHighestEarningEmployeeNames
6. createEmployee - POST http://localhost:8111/
   {
		"name": "Yash",
        "salary": 185000,
        "age": 25,
        "title": "Software Developer"
	}
7. deleteEmployeeById - DELETE http://localhost:8111/{id}

Here’s a brief overview of my approach:
1. Followed Test-Driven Development (TDD): I wrote test cases first, ensured they failed, and then implemented the functionality to make them pass. This helped ensure correctness from the start.

2. Focused on clean code and maintainability: Applied proper naming conventions, modular methods, and separation of concerns to make the code readable and easy to maintain.

3. Designed the application for scalability: Structured the project layers (Controller, Service, Repository) to allow easy extension for future features.

4. Implemented a well-organized architecture: Each module has a clear responsibility; validation, exception handling, and service logic are properly separated.

5. Comprehensive unit and integration tests: Ensured high coverage for all critical functionalities.

6. Adhered to the specifications mentioned in the README, including CRUD operations, input validations, and proper HTTP responses.

--------------------------------------------------------------------------------------------------------------------

#### In this assessment you will be tasked with filling out the functionality of different methods that will be listed further down.

These methods will require some level of API interactions with Mock Employee API at http://localhost:8112/api/v1/employee.

Please keep the following in mind when doing this assessment: 
* clean coding practices
* test driven development 
* logging
* scalability

See the section **How to Run Mock Employee API** for further instruction on starting the Mock Employee API.

### Endpoints to implement (API module)

_See `com.reliaquest.api.controller.IEmployeeController` for details._

getAllEmployees()

    output - list of employees
    description - this should return all employees

getEmployeesByNameSearch(...)

    path input - name fragment
    output - list of employees
    description - this should return all employees whose name contains or matches the string input provided

getEmployeeById(...)

    path input - employee ID
    output - employee
    description - this should return a single employee

getHighestSalaryOfEmployees()

    output - integer of the highest salary
    description - this should return a single integer indicating the highest salary of amongst all employees

getTop10HighestEarningEmployeeNames()

    output - list of employees
    description - this should return a list of the top 10 employees based off of their salaries

createEmployee(...)

    body input - attributes necessary to create an employee
    output - employee
    description - this should return a single employee, if created, otherwise error

deleteEmployeeById(...)

    path input - employee ID
    output - name of the employee
    description - this should delete the employee with specified id given, otherwise error

### Endpoints from Mock Employee API (Server module)

    request:
        method: GET
        full route: http://localhost:8112/api/v1/employee
    response:
        {
            "data": [
                {
                    "id": "4a3a170b-22cd-4ac2-aad1-9bb5b34a1507",
                    "employee_name": "Tiger Nixon",
                    "employee_salary": 320800,
                    "employee_age": 61,
                    "employee_title": "Vice Chair Executive Principal of Chief Operations Implementation Specialist",
                    "employee_email": "tnixon@company.com",
                },
                ....
            ],
            "status": "Successfully processed request."
        }
---
    request:
        method: GET
        path: 
            id (String)
        full route: http://localhost:8112/api/v1/employee/{id}
        note: 404-Not Found, if entity is unrecognizable
    response:
        {
            "data": {
                "id": "5255f1a5-f9f7-4be5-829a-134bde088d17",
                "employee_name": "Bill Bob",
                "employee_salary": 89750,
                "employee_age": 24,
                "employee_title": "Documentation Engineer",
                "employee_email": "billBob@company.com",
            },
            "status": ....
        }
---
    request:
        method: POST
        body: 
            name (String | not blank),
            salary (Integer | greater than zero),
            age (Integer | min = 16, max = 75),
            title (String | not blank)
        full route: http://localhost:8112/api/v1/employee
    response:
        {
            "data": {
                "id": "d005f39a-beb8-4390-afec-fd54e91d94ee",
                "employee_name": "Jill Jenkins",
                "employee_salary": 139082,
                "employee_age": 48,
                "employee_title": "Financial Advisor",
                "employee_email": "jillj@company.com",
            },
            "status": ....
        }
---
    request:
        method: DELETE
        body:
            name (String | not blank)
        full route: http://localhost:8112/api/v1/employee/{name}
    response:
        {
            "data": true,
            "status": ....
        }

### How to Run Mock Employee API (Server module)

Start **Server** Spring Boot application.
`./gradlew server:bootRun`

Each invocation of **Server** application triggers a new list of mock employee data. While live testing, you'll want to keep 
this server running if you require consistent data. Additionally, the web server will randomly choose when to rate
limit requests, so keep this mind when designing/implementing the actual Employee API.

_Note_: Console logs each mock employee upon startup.

### Code Formatting

This project utilizes Gradle plugin [Diffplug Spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle) to enforce format
and style guidelines with every build. 

To resolve any errors, you must run **spotlessApply** task.
`./gradlew spotlessApply`

