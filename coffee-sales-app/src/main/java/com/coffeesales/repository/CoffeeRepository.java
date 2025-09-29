package com.coffeesales.repository;

import com.coffeesales.model.Coffee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoffeeRepository implements PanacheRepository<Coffee> {
    // Heredamos todos los métodos básicos de PanacheRepository
    // find, findAll, count, delete, etc.
    
    public Coffee findByName(String name) {
        return find("name", name).firstResult();
    }
}