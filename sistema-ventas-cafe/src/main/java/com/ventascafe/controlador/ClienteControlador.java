package com.ventascafe.controlador;

import com.ventascafe.modelo.Cliente;
import com.ventascafe.servicio.ClienteServicio;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteControlador {
    
    @Inject
    ClienteServicio clienteServicio;
    
    @GET
    public List<Cliente> obtenerTodos() {
        return clienteServicio.obtenerTodos();
    }
    
    @GET
    @Path("/{id}")
    public Cliente obtenerPorId(@PathParam("id") Long id) {
        return clienteServicio.obtenerPorId(id);
    }
    
    @POST
    public Response crear(@Valid Cliente cliente) {
        Cliente clienteCreado = clienteServicio.crear(cliente);
        return Response.status(Response.Status.CREATED)
                .entity(clienteCreado)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    public Cliente actualizar(@PathParam("id") Long id, @Valid Cliente cliente) {
        return clienteServicio.actualizar(id, cliente);
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        clienteServicio.eliminar(id);
        return Response.noContent().build();
    }
}