package com.ventascafe.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta extends PanacheEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    public Cafe cafe;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    public Cliente cliente;
    
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    public Integer cantidad;
    
    @NotNull(message = "El precio unitario es requerido")
    @Positive(message = "El precio unitario debe ser mayor a cero")
    @Column(name = "precio_unitario", nullable = false)
    public BigDecimal precioUnitario;
    
    @Column(name = "monto_total", nullable = false)
    public BigDecimal montoTotal;
    
    @Column(name = "fecha_venta", nullable = false)
    public LocalDateTime fechaVenta;
    
    @PrePersist
    public void calcularMontoTotal() {
        if (cantidad != null && precioUnitario != null) {
            this.montoTotal = precioUnitario.multiply(new BigDecimal(cantidad));
        }
    }
}