package com.ventascafe.controlador;

import com.ventascafe.modelo.Venta;
import com.ventascafe.servicio.VentaServicio;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/ventas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VentaControlador {
    
    @Inject
    VentaServicio ventaServicio;
    
    @GET
    public List<Venta> obtenerTodas() {
        return ventaServicio.obtenerTodas();
    }
    
    @GET
    @Path("/{id}")
    public Venta obtenerPorId(@PathParam("id") Long id) {
        return ventaServicio.obtenerPorId(id);
    }
    
    @POST
    public Response crear(@Valid Venta venta) {
        Venta ventaCreada = ventaServicio.crear(venta);
        return Response.status(Response.Status.CREATED)
                .entity(ventaCreada)
                .build();
    }
    
    @GET
    @Path("/cliente/{clienteId}")
    public List<Venta> obtenerPorCliente(@PathParam("clienteId") Long clienteId) {
        return ventaServicio.obtenerPorCliente(clienteId);
    }
    
    @GET
    @Path("/cafe/{cafeId}")
    public List<Venta> obtenerPorCafe(@PathParam("cafeId") Long cafeId) {
        return ventaServicio.obtenerPorCafe(cafeId);
    }
    
    @GET
    @Path("/rango-fechas")
    public List<Venta> obtenerPorRangoFechas(
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
        LocalDateTime fin = LocalDateTime.parse(fechaFin);
        return ventaServicio.obtenerPorRangoFechas(inicio, fin);
    }
    
    @GET
    @Path("/ingresos")
    public BigDecimal calcularIngresos(
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
        LocalDateTime fin = LocalDateTime.parse(fechaFin);
        return ventaServicio.calcularIngresosTotales(inicio, fin);
    }
    
    @GET
    @Path("/ingresos/cliente/{clienteId}")
    public BigDecimal calcularIngresosCliente(
            @PathParam("clienteId") Long clienteId,
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
        LocalDateTime fin = LocalDateTime.parse(fechaFin);
        return ventaServicio.calcularIngresosCliente(clienteId, inicio, fin);
    }
}