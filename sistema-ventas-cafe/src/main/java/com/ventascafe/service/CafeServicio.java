package com.ventascafe.servicio;

import com.ventascafe.modelo.Cafe;
import com.ventascafe.repositorio.CafeRepositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class CafeServicio {
    
    @Inject
    CafeRepositorio cafeRepositorio;
    
    public List<Cafe> obtenerTodos() {
        return cafeRepositorio.listAll();
    }
    
    public Cafe obtenerPorId(Long id) {
        return cafeRepositorio.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Café no encontrado con id: " + id));
    }
    
    @Transactional
    public Cafe crear(Cafe cafe) {
        Cafe cafeExistente = cafeRepositorio.buscarPorNombre(cafe.nombre);
        if (cafeExistente != null) {
            throw new IllegalArgumentException("Ya existe un café con el nombre: " + cafe.nombre);
        }
        cafeRepositorio.persist(cafe);
        return cafe;
    }
    
    @Transactional
    public Cafe actualizar(Long id, Cafe cafe) {
        Cafe cafeExistente = obtenerPorId(id);
        cafeExistente.nombre = cafe.nombre;
        cafeExistente.descripcion = cafe.descripcion;
        cafeExistente.precio = cafe.precio;
        cafeExistente.origen = cafe.origen;
        cafeExistente.tipoTostado = cafe.tipoTostado;
        
        return cafeExistente;
    }
    
    @Transactional
    public void eliminar(Long id) {
        if (!cafeRepositorio.deleteById(id)) {
            throw new NotFoundException("Café no encontrado con id: " + id);
        }
    }
}