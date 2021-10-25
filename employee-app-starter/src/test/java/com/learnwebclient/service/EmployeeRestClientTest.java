package com.learnwebclient.service;

import com.learnwebclient.dto.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRestClientTest {

    private static final String baseUrl = "http://localhost:8081/employeeservice";
    private WebClient webClient = WebClient.create(baseUrl) ;

    EmployeeRestClient employeeRestClient = new EmployeeRestClient(webClient);

    @Test
    void retrieveAllEmployees(){
        List<Employee> employeeList = employeeRestClient.retrieveAllEmployees();
        System.out.println("empleeyList is: " + employeeList);
        assertTrue(employeeList.size() > 0);
    }

    @Test
    void retrieveEmployeeById(){

        int employeeId = 1;
        Employee employee = employeeRestClient.retrieveEmployeeById(employeeId);
        assertEquals("Chris", employee.getFirstName());
    }

    @Test
    void retrieveEmployeeById_notFound(){

        int employeeId = 10;
        Assertions.assertThrows(WebClientResponseException.class, ()->employeeRestClient.retrieveEmployeeById(employeeId));
    }

    @Test
    void retrieveEmployeeByName(){
        String name = "Chris";
        List<Employee> employees = employeeRestClient.retrieveEmployeeByName(name);
        assertTrue(employees.size() > 0);

        Employee  employee = employees.get(0);
        assertEquals("Chris", employee.getFirstName());
    }

    @Test
    void retrieveEmployeeByName_notFound(){
        String name = "ABC";
        Assertions.assertThrows(WebClientResponseException.class, ()->employeeRestClient.retrieveEmployeeByName(name));

    }

    @Test
    void addNewEmployee(){
        Employee employee = new Employee(null,"Iron", "Man", 54, "male", "Architect");

        Employee employee1 = employeeRestClient.addNewEmployee(employee);
        System.out.println("employee1  : " + employee1);
        assertNotNull(employee1.getId());

    }

    @Test
    void addNewEmployee_BadRequest(){
        Employee employee = new Employee(null,null, "Man", 54, "male", "Architect");

        String expectedErrorMessage = "Please pass all the input fields : [firstName]";
        Assertions.assertThrows(WebClientResponseException.class, () ->  employeeRestClient.addNewEmployee(employee), expectedErrorMessage);

    }
}
