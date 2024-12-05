package com.company.bitacora.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Alumnos")
public class Alumnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Nombre;
    private String Apellido;
    private String Matricula;
    private String Correo;
    private String contrasena;

    public Alumnos() {

    }

    public Alumnos(String Nombre, String Apellido, String Matricula, String Correo, String contrasena) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Matricula = Matricula;
        this.Correo = Correo;
        this.contrasena = contrasena;
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

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
