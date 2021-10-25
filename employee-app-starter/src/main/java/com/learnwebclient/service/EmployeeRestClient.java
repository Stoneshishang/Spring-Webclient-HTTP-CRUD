package com.learnwebclient.service;

import com.learnwebclient.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.learnwebclient.constants.EmployeeConstants.*;

@Slf4j
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

        try{
            return webClient.get().uri(EMPLOYEE_BY_ID_V1, employeeId)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        }catch(WebClientResponseException ex){
          log.error("Error Response Code is {} and response body is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
          log.error("WebClientResponseException in retrieveEmployeeById ", ex);
          throw ex;
        }catch (Exception ex){
            log.error("Exception in retrieveEmployeeById ", ex);
            throw ex;
        }
    }

//    http://localhost:8081/employeeservice/v1/employeeName?employee_name=ABC
    public List<Employee> retrieveEmployeeByName(String employeeName){

        String uri=UriComponentsBuilder.fromUriString(EMPLOYEE_BY_NAME_V1)
                .queryParam("employee_name", employeeName)
                .build().toUriString();

        try{
               return webClient.get().uri(uri)
                        .retrieve()
                        .bodyToFlux(Employee.class)
                        .collectList()
                        .block();
        }catch(WebClientResponseException ex){
            log.error("Error Response Code is {} and response body is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException in retrieveEmployeeByName ", ex);
            throw ex;
        }catch (Exception ex){
            log.error("Exception in retrieveEmployeeByName ", ex);
            throw ex;
        }

    }



    public Employee addNewEmployee(Employee employee) {
        try {
            return webClient.post().uri(ADD_EMPLOYEE_V1)
                    .syncBody(employee)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error Response code is : {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException in addNewEmployee", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in addNewEmployee ", ex);
            throw ex;
        }
    }


}
