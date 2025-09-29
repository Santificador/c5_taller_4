package com.coffeesales.service;

import com.coffeesales.model.Coffee;
import com.coffeesales.model.Customer;
import com.coffeesales.model.Sale;
import com.coffeesales.repository.SaleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class SaleService {
    
    @Inject
    SaleRepository saleRepository;
    
    @Inject
    CoffeeService coffeeService;
    
    @Inject
    CustomerService customerService;
    
    public List<Sale> getAllSales() {
        return saleRepository.listAll();
    }
    
    public Sale getSaleById(Long id) {
        return saleRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Sale not found with id: " + id));
    }
    
    @Transactional
    public Sale createSale(Sale sale) {
        // Validar que existan el café y el cliente
        Coffee coffee = coffeeService.getCoffeeById(sale.coffee.id);
        Customer customer = customerService.getCustomerById(sale.customer.id);
        
        sale.coffee = coffee;
        sale.customer = customer;
        sale.unitPrice = coffee.price;
        sale.saleDate = LocalDateTime.now();
        
        saleRepository.persist(sale);
        return sale;
    }
    
    public List<Sale> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findByDateRange(startDate, endDate);
    }
    
    public List<Sale> getSalesByCustomer(Long customerId) {
        return saleRepository.findByCustomerId(customerId);
    }
    
    public List<Sale> getSalesByCoffee(Long coffeeId) {
        return saleRepository.findByCoffeeId(coffeeId);
    }
    
    public BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sale> sales = saleRepository.findByDateRange(startDate, endDate);
        return sales.stream()
                .map(sale -> sale.totalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal calculateCustomerRevenue(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Sale> sales = saleRepository.findByDateRangeAndCustomerId(startDate, endDate, customerId);
        return sales.stream()
                .map(sale -> sale.totalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}