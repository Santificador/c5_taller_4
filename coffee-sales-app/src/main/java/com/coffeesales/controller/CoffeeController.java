package com.coffeesales.controller;

import com.coffeesales.model.Coffee;
import com.coffeesales.service.CoffeeService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/coffees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeController {
    
    @Inject
    CoffeeService coffeeService;
    
    @GET
    public List<Coffee> getAllCoffees() {
        return coffeeService.getAllCoffees();
    }
    
    @GET
    @Path("/{id}")
    public Coffee getCoffeeById(@PathParam("id") Long id) {
        return coffeeService.getCoffeeById(id);
    }
    
    @POST
    public Response createCoffee(@Valid Coffee coffee) {
        Coffee createdCoffee = coffeeService.createCoffee(coffee);
        return Response.status(Response.Status.CREATED)
                .entity(createdCoffee)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    public Coffee updateCoffee(@PathParam("id") Long id, @Valid Coffee coffee) {
        return coffeeService.updateCoffee(id, coffee);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCoffee(@PathParam("id") Long id) {
        coffeeService.deleteCoffee(id);
        return Response.noContent().build();
    }
}