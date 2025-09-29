package com.coffeesales.service;

import com.coffeesales.model.Customer;
import com.coffeesales.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class CustomerService {
    
    @Inject
    CustomerRepository customerRepository;
    
    public List<Customer> getAllCustomers() {
        return customerRepository.listAll();
    }
    
    public Customer getCustomerById(Long id) {
        return customerRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
    }
    
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.email)) {
            throw new IllegalArgumentException("Customer with email " + customer.email + " already exists");
        }
        customerRepository.persist(customer);
        return customer;
    }
    
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
        
        // Verificar si el nuevo email ya existe para otro cliente
        if (!existingCustomer.email.equals(customer.email) && 
            customerRepository.existsByEmail(customer.email)) {
            throw new IllegalArgumentException("Email " + customer.email + " is already in use");
        }
        
        existingCustomer.firstName = customer.firstName;
        existingCustomer.lastName = customer.lastName;
        existingCustomer.email = customer.email;
        existingCustomer.phoneNumber = customer.phoneNumber;
        existingCustomer.address = customer.address;
        
        return existingCustomer;
    }
    
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.deleteById(id)) {
            throw new NotFoundException("Customer not found with id: " + id);
        }
    }
}