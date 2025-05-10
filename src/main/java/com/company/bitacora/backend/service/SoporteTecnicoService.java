package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.SoporteTecnico;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SoporteTecnicoService {
    List<SoporteTecnico> listarTecnicos();

    SoporteTecnico getSoporteTecnico(Long id);

    SoporteTecnico saveSoporteTecnico(SoporteTecnico soporteTecnico);

    SoporteTecnico updateSoporteTecnico(String correo, SoporteTecnico soporteTecnico);

    @Transactional
    boolean deleteSoporteTecnico(String correo);
}
