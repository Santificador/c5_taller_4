package com.ventascafe.repositorio;

import com.ventascafe.modelo.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class ClienteRepositorio implements PanacheRepository<Cliente> {
    
    public Optional<Cliente> buscarPorEmail(String email) {
        return find("email", email).firstResultOptional();
    }
    
    public boolean existePorEmail(String email) {
        return count("email", email) > 0;
    }
}