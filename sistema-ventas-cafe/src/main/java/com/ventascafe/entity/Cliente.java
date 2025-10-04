package com.ventascafe.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "clientes")
public class Cliente extends PanacheEntity {
    
    @NotBlank(message = "El nombre es requerido")
    @Column(name = "nombre", nullable = false)
    public String nombre;
    
    @NotBlank(message = "El apellido es requerido")
    @Column(name = "apellido", nullable = false)
    public String apellido;
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    @Column(unique = true, nullable = false)
    public String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El número de teléfono debe tener un formato válido")
    @Column(name = "telefono")
    public String telefono;
    
    @Column(name = "direccion", nullable = false)
    public String direccion;
}