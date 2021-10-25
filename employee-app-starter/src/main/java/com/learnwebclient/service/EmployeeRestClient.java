package com.learnwebclient.service;

import com.learnwebclient.dto.Employee;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.learnwebclient.constants.EmployeeConstants.EMPLOYEE_BY_ID_V1;
import static com.learnwebclient.constants.EmployeeConstants.GET_ALL_EMPLOYEES_V1;

public class EmployeeRestClient {

    private WebClient webClient;

    public EmployeeRestClient(WebClient webClient){
        this.webClient = webClient;
    }

//    http://localhost:8081/employeeservice/v1/allEmployees

    public List<Employee> retrieveAllEmployees(){

       return webClient.get().uri(GET_ALL_EMPLOYEES_V1)
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();
    }

    public Employee retrieveEmployeeById(int employeeId){

       return webClient.get().uri(EMPLOYEE_BY_ID_V1, employeeId)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
    }

}
