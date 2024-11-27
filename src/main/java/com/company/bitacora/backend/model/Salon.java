package com.company.bitacora.backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Salon")
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Nombre;
    private String Ubicacion;
    private String Capacidad;

    public Salon() {

    }

    public Salon(String Nombre, String Ubicacion, String Capacidad) {
        this.Nombre = Nombre;
        this.Ubicacion = Ubicacion;
        this.Capacidad = Capacidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String Ubicacion) {
        this.Ubicacion = Ubicacion;
    }

    public String getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(String Capacidad) {
        this.Capacidad = Capacidad;
    }
}
