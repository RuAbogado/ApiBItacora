package com.company.bitacora.backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String Marca;
    String Modelo;

    public Equipo() {
    }

    public Equipo(String Marca, String Modelo) {
        this.Marca = Marca;
        this.Modelo = Modelo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMarca() {
        return Marca;
    }
    public void setMarca(String Marca) {
        this.Marca = Marca;
    }
    public String getModelo() {
        return Modelo;
    }
    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }
}
