package com.coffeesales.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class Sale extends PanacheEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id", nullable = false)
    public Coffee coffee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;
    
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    public Integer quantity;
    
    @NotNull(message = "El precio unitario es requerido")
    @Positive(message = "El precio unitario debe ser mayor a cero")
    @Column(name = "unit_price", nullable = false)
    public BigDecimal unitPrice;
    
    @Column(name = "total_amount", nullable = false)
    public BigDecimal totalAmount;
    
    @Column(name = "sale_date", nullable = false)
    public LocalDateTime saleDate;
    
    @PrePersist
    public void calculateTotalAmount() {
        if (quantity != null && unitPrice != null) {
            this.totalAmount = unitPrice.multiply(new BigDecimal(quantity));
        }
    }
}