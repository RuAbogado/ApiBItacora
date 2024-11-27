package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.SoporteTecnico;

import java.util.List;

public interface SoporteTecnicoService {
    List<SoporteTecnico> listarTecnicos();

    SoporteTecnico getSoporteTecnico(Long id);

    SoporteTecnico saveSoporteTecnico(SoporteTecnico soporteTecnico);

    SoporteTecnico updateSoporteTecnico(Long id, SoporteTecnico soporteTecnico);

    void deleteSoporteTecnico(Long id);

}
