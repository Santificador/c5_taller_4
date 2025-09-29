package com.coffeesales.repository;

import com.coffeesales.model.Sale;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class SaleRepository implements PanacheRepository<Sale> {
    
    public List<Sale> findByCustomerId(Long customerId) {
        return list("customer.id", customerId);
    }
    
    public List<Sale> findByCoffeeId(Long coffeeId) {
        return list("coffee.id", coffeeId);
    }
    
    public List<Sale> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return list("saleDate >= ?1 and saleDate <= ?2", startDate, endDate);
    }
    
    public List<Sale> findByDateRangeAndCustomerId(LocalDateTime startDate, LocalDateTime endDate, Long customerId) {
        return list("saleDate >= ?1 and saleDate <= ?2 and customer.id = ?3", 
                   startDate, endDate, customerId);
    }
}