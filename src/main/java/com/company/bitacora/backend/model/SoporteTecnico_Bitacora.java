package com.company.bitacora.backend.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "SoporteTecnico_Bitacora")
public class SoporteTecnico_Bitacora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime Fecha_Atencion;
    private String Observaciones;

    public SoporteTecnico_Bitacora() {

    }

    public SoporteTecnico_Bitacora(LocalTime Fecha_Atencion, String Observaciones) {
        this.Fecha_Atencion = Fecha_Atencion;
        this.Observaciones = Observaciones;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getFecha_Atencion() {
        return Fecha_Atencion;
    }

    public void setFecha_Atencion(LocalTime fecha_Atencion) {
        Fecha_Atencion = fecha_Atencion;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }
}
