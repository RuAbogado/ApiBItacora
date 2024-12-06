package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Equipo;

import java.util.List;

public interface EquipoService {
    List<Equipo> getEquipos();

    Equipo getEquipoById(Long id);

    Equipo saveEquipo(Equipo equipo);

    Equipo updateEquipo(Long id, Equipo equipo);

    boolean deleteEquipo(Long id);
}
