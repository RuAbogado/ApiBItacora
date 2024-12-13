package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Bitacora;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BitacoraService {

    // Buscar todas las bitácoras
    List<Bitacora> buscarBitacoras();

    // Buscar una bitácora por ID
    Bitacora buscarPorID(Long id);

    // Crear una nueva bitácora (modificado para recibir MultipartFile para la imagen)
    Bitacora crearBitacora(Long idSalon, Long idEquipo, String correo, String fecha,
                           String horaEntrada, String horaSalida, String maestro,
                           String grado, String grupo, String observaciones,
                           MultipartFile img);

    // Actualizar una bitácora existente
    Bitacora actualizarBitacora(Bitacora bitacora, Long id);

    // Subir una imagen para una bitacora
    Bitacora upImagen(MultipartFile img, Long id);

    // Eliminar una bitacora
    boolean eliminarBitacora(Long id);
}
