package com.coffeesales.controller;

import com.coffeesales.model.Customer;
import com.coffeesales.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {
    
    @Inject
    CustomerService customerService;
    
    @GET
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    
    @GET
    @Path("/{id}")
    public Customer getCustomerById(@PathParam("id") Long id) {
        return customerService.getCustomerById(id);
    }
    
    @POST
    public Response createCustomer(@Valid Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return Response.status(Response.Status.CREATED)
                .entity(createdCustomer)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    public Customer updateCustomer(@PathParam("id") Long id, @Valid Customer customer) {
        return customerService.updateCustomer(id, customer);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        customerService.deleteCustomer(id);
        return Response.noContent().build();
    }
}