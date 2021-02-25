package org.acme;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/employee")
public class EmployeeResource {

    @Inject
    KafkaService kafkaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAll() {
        return Employee.listAll();
    }

    @GET
    @Path("{id}")
    public Employee getSingle(@PathParam("id") Long id) {
        Employee entity = Employee.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Employee with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Employee employee) {
        employee.persist();
        kafkaService.publishAsJson(employee, 0);
        return Response.status(Response.Status.CREATED).entity(employee).build();
    }

    @Transactional
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee update(@PathParam("id") Long id, Employee employee) {
        Employee entity = Employee.findById(id);
        entity.name = employee.name;
        entity.age = employee.age;
        entity.persist();
        kafkaService.publishAsJson(entity, 0);
        return entity;
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Employee entity = Employee.findById(id);
        entity.delete();
        if (entity == null) {
            throw new WebApplicationException("Employee with id of " + id + " does not exist.", 404);
        }
        return Response.status(Response.Status.OK).build();
    }

    /*
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getById(@PathParam("id") String id){
        return employeeService.getById(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Employee create(Employee employeeToCreate){
        return employeeService.create(employeeToCreate);
    }
     */
}