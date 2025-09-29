package com.ventascafe.repositorio;

import com.ventascafe.modelo.Cafe;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CafeRepositorio implements PanacheRepository<Cafe> {
    
    public Cafe buscarPorNombre(String nombre) {
        return find("nombre", nombre).firstResult();
    }
}