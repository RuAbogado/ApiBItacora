package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.response.BitacoraResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BitacoraService {
    ResponseEntity<BitacoraResponseRest> buscarBitacora();
    ResponseEntity<BitacoraResponseRest> buscarPorID(Long id);
    ResponseEntity<BitacoraResponseRest> crearBitacora(Bitacora bitacora);
    ResponseEntity<BitacoraResponseRest> actualizarBitacora(Bitacora bitacora, Long id);
    ResponseEntity<BitacoraResponseRest> eliminarBitacora(Long id);
    ResponseEntity<BitacoraResponseRest> upImagen(MultipartFile img, Long id);

}
