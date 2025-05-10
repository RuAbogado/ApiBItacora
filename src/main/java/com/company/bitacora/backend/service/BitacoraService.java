package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Bitacora;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BitacoraService {

    // Buscar todas las bitácoras
    List<Bitacora> buscarBitacoras();

    // Buscar una bitácora por ID
    Bitacora buscarPorID(Long id);

    // Crear una nueva bitácora
    Bitacora crearBitacora(Bitacora bitacora);

    // Actualizar una bitácora existente
    Bitacora actualizarBitacora(Bitacora bitacora, Long id);

    // Subir una imagen para una bitácora
    Bitacora upImagen(MultipartFile img, Long id);

    // Eliminar una bitácora
    boolean eliminarBitacora(Long id);
}
