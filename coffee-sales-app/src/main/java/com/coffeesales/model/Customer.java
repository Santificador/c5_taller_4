package com.coffeesales.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "customers")
public class Customer extends PanacheEntity {
    
    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false)
    public String firstName;
    
    @NotBlank(message = "El apellido es requerido")
    @Column(nullable = false)
    public String lastName;
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    @Column(unique = true, nullable = false)
    public String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El número de teléfono debe tener un formato válido")
    @Column(name = "phone_number")
    public String phoneNumber;
    
    @Column(nullable = false)
    public String address;
}