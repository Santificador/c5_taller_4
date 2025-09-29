package com.coffeesales.service;

import com.coffeesales.model.Coffee;
import com.coffeesales.repository.CoffeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class CoffeeService {
    
    @Inject
    CoffeeRepository coffeeRepository;
    
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.listAll();
    }
    
    public Coffee getCoffeeById(Long id) {
        return coffeeRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Coffee not found with id: " + id));
    }
    
    @Transactional
    public Coffee createCoffee(Coffee coffee) {
        Coffee existingCoffee = coffeeRepository.findByName(coffee.name);
        if (existingCoffee != null) {
            throw new IllegalArgumentException("Coffee with name " + coffee.name + " already exists");
        }
        coffeeRepository.persist(coffee);
        return coffee;
    }
    
    @Transactional
    public Coffee updateCoffee(Long id, Coffee coffee) {
        Coffee existingCoffee = getCoffeeById(id);
        existingCoffee.name = coffee.name;
        existingCoffee.description = coffee.description;
        existingCoffee.price = coffee.price;
        existingCoffee.origin = coffee.origin;
        existingCoffee.roastType = coffee.roastType;
        
        return existingCoffee;
    }
    
    @Transactional
    public void deleteCoffee(Long id) {
        if (!coffeeRepository.deleteById(id)) {
            throw new NotFoundException("Coffee not found with id: " + id);
        }
    }
}