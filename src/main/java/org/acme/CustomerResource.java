package org.acme;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
public class CustomerResource {

    @Inject
    KafkaService kafkaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAll() {
        return Customer.listAll();
    }

    @GET
    @Path("{id}")
    public Customer getSingle(@PathParam("id") Long id) {
        Customer entity = Customer.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Customer with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Customer customer) {
        customer.persist();
        kafkaService.publishAsJson(customer, 1);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @Transactional
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer update(@PathParam("id") Long id, Customer customer) {
        Customer entity = customer.findById(id);
        entity.name = customer.name;
        entity.age = customer.age;
        entity.persist();
        kafkaService.publishAsJson(entity, 1);
        return entity;
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Customer entity = Customer.findById(id);
        entity.delete();
        if (entity == null) {
            throw new WebApplicationException("Customer with id of " + id + " does not exist.", 404);
        }
        return Response.status(Response.Status.OK).build();
    }

    /*
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getById(@PathParam("id") String id){
        return CustomerService.getById(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Customer create(Customer CustomerToCreate){
        return CustomerService.create(CustomerToCreate);
    }
     */
}