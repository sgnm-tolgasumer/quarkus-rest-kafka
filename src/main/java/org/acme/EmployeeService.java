package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmployeeService {
    public List employees = new ArrayList();

    @Inject
    KafkaService kafkaService;


    public Employee create(Employee employeeToCreate){
        employees.add(employeeToCreate);
        kafkaService.publishAsJson(employeeToCreate);
        return employeeToCreate;
    }


    public List getAll(){
        return employees;
    }
}
