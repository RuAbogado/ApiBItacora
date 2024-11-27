package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.response.BitacoraResponseRest;
import org.springframework.http.ResponseEntity;

public interface BitacoraService {
    ResponseEntity<BitacoraResponseRest> buscarBitacora();
    ResponseEntity<BitacoraResponseRest> buscarPorID(Long id);
    ResponseEntity<BitacoraResponseRest> crearBitacora(Bitacora bitacora);
    ResponseEntity<BitacoraResponseRest> actualizarBitacora(Bitacora bitacora, Long id);
    ResponseEntity<BitacoraResponseRest> eliminarBitacora(Long id);
}
