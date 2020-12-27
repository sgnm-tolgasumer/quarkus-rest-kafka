package org.acme;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmployeeService {
    public List employees = new ArrayList();

    public Employee create(Employee employeeToCreate){
        employees.add(employeeToCreate);
        return employeeToCreate;
    }

    public List getAll(){
        return employees;
    }
}
