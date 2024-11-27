package com.company.bitacora.backend.service;


import com.company.bitacora.backend.model.SoporteTecnico_Bitacora;

import java.util.List;

public interface SoporteTecnico_BitacoraService {
    List<SoporteTecnico_Bitacora> BitacorasTecnicos();

    SoporteTecnico_Bitacora BuscarBitacora(Long id);

    SoporteTecnico_Bitacora NuevaBitacora(SoporteTecnico_Bitacora SoporteTecnico_Bitacora);

    SoporteTecnico_Bitacora actualizarBitacora(Long id, SoporteTecnico_Bitacora SoporteTecnico_Bitacora);

    void eliminarBitacora(Long id);
}
