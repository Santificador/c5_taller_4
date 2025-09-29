package com.ventascafe.servicio;

import com.ventascafe.modelo.Cafe;
import com.ventascafe.modelo.Cliente;
import com.ventascafe.modelo.Venta;
import com.ventascafe.repositorio.VentaRepositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class VentaServicio {
    
    @Inject
    VentaRepositorio ventaRepositorio;
    
    @Inject
    CafeServicio cafeServicio;
    
    @Inject
    ClienteServicio clienteServicio;
    
    public List<Venta> obtenerTodas() {
        return ventaRepositorio.listAll();
    }
    
    public Venta obtenerPorId(Long id) {
        return ventaRepositorio.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada con id: " + id));
    }
    
    @Transactional
    public Venta crear(Venta venta) {
        Cafe cafe = cafeServicio.obtenerPorId(venta.cafe.id);
        Cliente cliente = clienteServicio.obtenerPorId(venta.cliente.id);
        
        venta.cafe = cafe;
        venta.cliente = cliente;
        venta.precioUnitario = cafe.precio;
        venta.fechaVenta = LocalDateTime.now();
        
        ventaRepositorio.persist(venta);
        return venta;
    }
    
    public List<Venta> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepositorio.buscarPorRangoFechas(fechaInicio, fechaFin);
    }
    
    public List<Venta> obtenerPorCliente(Long clienteId) {
        return ventaRepositorio.buscarPorClienteId(clienteId);
    }
    
    public List<Venta> obtenerPorCafe(Long cafeId) {
        return ventaRepositorio.buscarPorCafeId(cafeId);
    }
    
    public BigDecimal calcularIngresosTotales(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Venta> ventas = ventaRepositorio.buscarPorRangoFechas(fechaInicio, fechaFin);
        return ventas.stream()
                .map(venta -> venta.montoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal calcularIngresosCliente(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Venta> ventas = ventaRepositorio.buscarPorRangoFechasYClienteId(fechaInicio, fechaFin, clienteId);
        return ventas.stream()
                .map(venta -> venta.montoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}