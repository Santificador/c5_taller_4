package com.ventascafe.controlador;

import com.ventascafe.modelo.Cafe;
import com.ventascafe.servicio.CafeServicio;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/cafes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CafeControlador {
    
    @Inject
    CafeServicio cafeServicio;
    
    @GET
    public List<Cafe> obtenerTodos() {
        return cafeServicio.obtenerTodos();
    }
    
    @GET
    @Path("/{id}")
    public Cafe obtenerPorId(@PathParam("id") Long id) {
        return cafeServicio.obtenerPorId(id);
    }
    
    @POST
    public Response crear(@Valid Cafe cafe) {
        Cafe cafeCreado = cafeServicio.crear(cafe);
        return Response.status(Response.Status.CREATED)
                .entity(cafeCreado)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    public Cafe actualizar(@PathParam("id") Long id, @Valid Cafe cafe) {
        return cafeServicio.actualizar(id, cafe);
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        cafeServicio.eliminar(id);
        return Response.noContent().build();
    }
}