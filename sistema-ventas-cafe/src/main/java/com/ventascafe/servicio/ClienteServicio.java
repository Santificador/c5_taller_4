package com.ventascafe.servicio;

import com.ventascafe.modelo.Cliente;
import com.ventascafe.repositorio.ClienteRepositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class ClienteServicio {
    
    @Inject
    ClienteRepositorio clienteRepositorio;
    
    public List<Cliente> obtenerTodos() {
        return clienteRepositorio.listAll();
    }
    
    public Cliente obtenerPorId(Long id) {
        return clienteRepositorio.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con id: " + id));
    }
    
    @Transactional
    public Cliente crear(Cliente cliente) {
        if (clienteRepositorio.existePorEmail(cliente.email)) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + cliente.email);
        }
        clienteRepositorio.persist(cliente);
        return cliente;
    }
    
    @Transactional
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente clienteExistente = obtenerPorId(id);
        
        if (!clienteExistente.email.equals(cliente.email) && 
            clienteRepositorio.existePorEmail(cliente.email)) {
            throw new IllegalArgumentException("El email " + cliente.email + " ya está en uso");
        }
        
        clienteExistente.nombre = cliente.nombre;
        clienteExistente.apellido = cliente.apellido;
        clienteExistente.email = cliente.email;
        clienteExistente.telefono = cliente.telefono;
        clienteExistente.direccion = cliente.direccion;
        
        return clienteExistente;
    }
    
    @Transactional
    public void eliminar(Long id) {
        if (!clienteRepositorio.deleteById(id)) {
            throw new NotFoundException("Cliente no encontrado con id: " + id);
        }
    }
}