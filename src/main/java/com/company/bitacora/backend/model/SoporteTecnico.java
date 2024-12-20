package com.company.bitacora.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SoporteTecnico")
public class SoporteTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String Nombre;
    String Apellido;
    String Correo;
    String Contrasena;
    String Tipo;

    public SoporteTecnico() {

    }

    public SoporteTecnico(String Nombre, String Apellido, String Correo, String Contrasena, String Tipo) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Correo = Correo;
        this.Contrasena = Contrasena;
        this.Tipo = Tipo;

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

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String Contrasena) {
        this.Correo = Contrasena;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
}
