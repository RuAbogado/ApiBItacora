package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.model.Equipo;
import com.company.bitacora.backend.model.Salon;
import com.company.bitacora.backend.model.dao.BitacoraDao;
import com.company.bitacora.backend.model.dao.EquipoDao;
import com.company.bitacora.backend.model.dao.SalonDao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    @Autowired
    private BitacoraDao bitacoraDao;

    @Autowired
    private SalonDao salonDao;

    @Autowired
    private EquipoDao equipoDao;

    @Override
    public List<Bitacora> buscarBitacoras() {
        return (List<Bitacora>) bitacoraDao.findAll();
    }

    @Override
    public Bitacora buscarPorID(Long id) {
        return bitacoraDao.findById(id).orElse(null);
    }

    @Override
    public Bitacora crearBitacora(Long idSalon, Long idEquipo, String correo, String fecha,
                                  String horaEntrada, String horaSalida, String maestro,
                                  String grado, String grupo, String observaciones,
                                  MultipartFile img) {
        // Buscar salón y equipo
        Salon salon = salonDao.findById(idSalon)
                .orElseThrow(() -> new EntityNotFoundException("Salón no encontrado con ID: " + idSalon));
        Equipo equipo = equipoDao.findById(idEquipo)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + idEquipo));

        // Validar los parámetros
        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }
        if (maestro == null || maestro.isEmpty()) {
            throw new IllegalArgumentException("El nombre del maestro no puede estar vacío.");
        }
        if (grado == null || grado.isEmpty()) {
            throw new IllegalArgumentException("El grado no puede estar vacío.");
        }

        // Convertir los parámetros de fecha y hora
        LocalDate fechaLocal = LocalDate.parse(fecha);  // Puede lanzar DateTimeParseException si el formato no es válido
        LocalTime horaEntradaLocal = LocalTime.parse(horaEntrada);  // Puede lanzar DateTimeParseException
        LocalTime horaSalidaLocal = LocalTime.parse(horaSalida);  // Puede lanzar DateTimeParseException

        // Convertir la imagen a bytes
        byte[] fotoBytes = null;
        if (img != null && !img.isEmpty()) {
            try {
                fotoBytes = img.getBytes(); // Convertir la imagen en un arreglo de bytes
            } catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo de imagen", e);
            }
        }

        // Crear la bitácora
        Bitacora bitacora = new Bitacora();
        bitacora.setFecha(fechaLocal);
        bitacora.setHoraEntrada(horaEntradaLocal);
        bitacora.setHoraSalida(horaSalidaLocal);
        bitacora.setMaestro(maestro);
        bitacora.setGrado(grado);
        bitacora.setGrupo(grupo);
        bitacora.setDescripcion(observaciones);  // Aquí debes usar observaciones en lugar de descripcion
        bitacora.setFoto(fotoBytes);
        bitacora.setSalon(salon);
        bitacora.setEquipo(equipo);

        // Guardar y retornar la bitácora
        return bitacoraDao.save(bitacora);
    }


    @Override
    public Bitacora actualizarBitacora(Bitacora bitacora, Long id) {
        if (bitacoraDao.existsById(id)) {
            bitacora.setId(id);
            return bitacoraDao.save(bitacora);
        }
        return null;
    }

    @Override
    public Bitacora upImagen(MultipartFile img, Long id) {
        Optional<Bitacora> existingBitacora = bitacoraDao.findById(id);
        if (existingBitacora.isPresent()) {
            try {
                Bitacora bitacora = existingBitacora.get();
                bitacora.setFoto(img.getBytes());
                return bitacoraDao.save(bitacora);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen", e);
            }
        }
        return null;
    }

    @Override
    public boolean eliminarBitacora(Long id) {
        if (bitacoraDao.existsById(id)) {
            bitacoraDao.deleteById(id);
            return true;
        }
        return false;
    }
}
