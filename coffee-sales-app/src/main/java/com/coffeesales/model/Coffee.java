package com.coffeesales.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "coffees")
public class Coffee extends PanacheEntity {
    
    @NotBlank(message = "El nombre del café es requerido")
    @Column(nullable = false)
    public String name;
    
    @NotBlank(message = "La descripción del café es requerida")
    @Column(nullable = false)
    public String description;
    
    @NotNull(message = "El precio del café es requerido")
    @Positive(message = "El precio debe ser mayor a cero")
    @Column(nullable = false)
    public BigDecimal price;
    
    @Column(nullable = false)
    public String origin;
    
    @NotBlank(message = "El tipo de tostado es requerido")
    @Column(name = "roast_type", nullable = false)
    public String roastType;
}