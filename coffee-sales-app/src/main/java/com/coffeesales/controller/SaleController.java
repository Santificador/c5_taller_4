package com.coffeesales.controller;

import com.coffeesales.model.Sale;
import com.coffeesales.service.SaleService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/sales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SaleController {
    
    @Inject
    SaleService saleService;
    
    @GET
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }
    
    @GET
    @Path("/{id}")
    public Sale getSaleById(@PathParam("id") Long id) {
        return saleService.getSaleById(id);
    }
    
    @POST
    public Response createSale(@Valid Sale sale) {
        Sale createdSale = saleService.createSale(sale);
        return Response.status(Response.Status.CREATED)
                .entity(createdSale)
                .build();
    }
    
    @GET
    @Path("/customer/{customerId}")
    public List<Sale> getSalesByCustomer(@PathParam("customerId") Long customerId) {
        return saleService.getSalesByCustomer(customerId);
    }
    
    @GET
    @Path("/coffee/{coffeeId}")
    public List<Sale> getSalesByCoffee(@PathParam("coffeeId") Long coffeeId) {
        return saleService.getSalesByCoffee(coffeeId);
    }
    
    @GET
    @Path("/date-range")
    public List<Sale> getSalesByDateRange(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return saleService.getSalesByDateRange(start, end);
    }
    
    @GET
    @Path("/revenue")
    public BigDecimal calculateRevenue(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return saleService.calculateTotalRevenue(start, end);
    }
    
    @GET
    @Path("/revenue/customer/{customerId}")
    public BigDecimal calculateCustomerRevenue(
            @PathParam("customerId") Long customerId,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return saleService.calculateCustomerRevenue(customerId, start, end);
    }
}