package com.learnwebclient.service;

import com.learnwebclient.dto.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
