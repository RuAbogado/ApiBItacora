package com.company.bitacora.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Alumnos")
public class Alumnos {
    @Id
    @Column(nullable = false, unique = true)
    private String correo;

    private String Nombre;
    private String Apellido;
    private String Matricula;
    private String contrasena;

    public Alumnos() {
    }

    public Alumnos(String Nombre, String Apellido, String Matricula, String correo, String contrasena) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Matricula = Matricula;
        this.correo = correo;
        this.contrasena = contrasena;
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
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
