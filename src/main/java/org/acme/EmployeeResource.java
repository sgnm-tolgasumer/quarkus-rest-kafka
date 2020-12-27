package org.acme;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/employee")
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List getAll() {
        return employeeService.getAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Employee create(Employee employeeToCreate){
        return employeeService.create(employeeToCreate);
    }
}