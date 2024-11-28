package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.model.dao.BitacoraDao;
import com.company.bitacora.backend.response.BitacoraResponseRest;
import com.company.bitacora.backend.response.ResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    private static final Logger log = LoggerFactory.getLogger(BitacoraServiceImpl.class);

    @Autowired
    private BitacoraDao bitacoraDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<BitacoraResponseRest> buscarBitacora() {
        log.info("Buscando todas las bitácoras...");
        BitacoraResponseRest response = new BitacoraResponseRest();
        try {
            List<Bitacora> bitacoras = (List<Bitacora>) bitacoraDao.findAll();
            response.setBitacora(bitacoras);
            response.setMetadata("Respuesta OK", "00", "Consulta exitosa");
        } catch (Exception e) {
            log.error("Error al consultar las bitácoras: ", e);
            response.setMetadata("Error", "-1", "Error al consultar las bitácoras");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<BitacoraResponseRest> buscarPorID(Long id) {
        log.info("Buscando bitácora con ID: {}", id);
        BitacoraResponseRest response = new BitacoraResponseRest();
        List<Bitacora> lista = new ArrayList<>();
        try {
            Optional<Bitacora> bitacora = bitacoraDao.findById(id);
            if (bitacora.isPresent()) {
                lista.add(bitacora.get());
                response.setBitacora(lista);
                response.setMetadata("Respuesta OK", "00", "Consulta exitosa");
            } else {
                response.setMetadata("Respuesta No Encontrada", "-1", "Bitácora no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al consultar bitácora: ", e);
            response.setMetadata("Error", "-1", "Error al consultar la bitácora");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<BitacoraResponseRest> crearBitacora(Bitacora bitacora) {
        log.info("Creando nueva bitácora...");
        BitacoraResponseRest response = new BitacoraResponseRest();
        List<Bitacora> lista = new ArrayList<>();
        try {
            Bitacora nuevaBitacora = bitacoraDao.save(bitacora);
            lista.add(nuevaBitacora);
            response.setBitacora(lista);
            response.setMetadata("Respuesta OK", "00", "Creación exitosa");
        } catch (Exception e) {
            log.error("Error al crear la bitácora: ", e);
            response.setMetadata("Error", "-1", "Error al guardar la bitácora");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<BitacoraResponseRest> actualizarBitacora(Bitacora bitacora, Long id) {
        log.info("Actualizando bitácora con ID: {}", id);
        BitacoraResponseRest response = new BitacoraResponseRest();
        List<Bitacora> lista = new ArrayList<>();
        try {
            Optional<Bitacora> bitacoraExistente = bitacoraDao.findById(id);
            if (bitacoraExistente.isPresent()) {
                Bitacora actualizarBitacora = bitacoraExistente.get();
                actualizarBitacora.setFecha(bitacora.getFecha());
                actualizarBitacora.setHoraEntrada(bitacora.getHoraEntrada());
                actualizarBitacora.setHoraSalida(bitacora.getHoraSalida());
                actualizarBitacora.setMaestro(bitacora.getMaestro());
                actualizarBitacora.setGrado(bitacora.getGrado());
                actualizarBitacora.setGrupo(bitacora.getGrupo());
                actualizarBitacora.setFoto(bitacora.getFoto());
                actualizarBitacora.setDescripcion(bitacora.getDescripcion());

                Bitacora bitacoraActualizada = bitacoraDao.save(actualizarBitacora);
                lista.add(bitacoraActualizada);
                response.setBitacora(lista);
                response.setMetadata("Respuesta OK", "00", "Actualización exitosa");
            } else {
                response.setMetadata("Respuesta No Encontrada", "-1", "Bitácora no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al actualizar bitácora: ", e);
            response.setMetadata("Error", "-1", "Error al actualizar la bitácora");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<BitacoraResponseRest> eliminarBitacora(Long id) {
        log.info("Eliminando bitácora con ID: {}", id);
        BitacoraResponseRest response = new BitacoraResponseRest();
        try {
            Optional<Bitacora> bitacora = bitacoraDao.findById(id);
            if (bitacora.isPresent()) {
                bitacoraDao.delete(bitacora.get());
                response.setMetadata("Respuesta OK", "00", "Eliminación exitosa");
            } else {
                response.setMetadata("Respuesta No Encontrada", "-1", "Bitácora no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al eliminar bitácora: ", e);
            response.setMetadata("Error", "-1", "Error al eliminar la bitácora");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<BitacoraResponseRest> upImagen(MultipartFile img, Long id) {
        log.info("Hola!");
        byte[] bytesImg = null;

        BitacoraResponseRest response = new BitacoraResponseRest();
        List<Bitacora> list = new ArrayList<>();

        try {
            if(!img.isEmpty()){
                bytesImg = img.getBytes();
            }
            Optional<Bitacora> categoriaBuscada = bitacoraDao.findById(id);
            if(categoriaBuscada.isPresent()) {
                categoriaBuscada.get().setFoto(bytesImg);

                Bitacora categoriaActualizar = bitacoraDao.save(categoriaBuscada.get());

                if(categoriaActualizar != null) {
                    list.add(categoriaActualizar);
                    response.getBitacora();
                    response.setBitacora(list);
                    response.setMetadata("Respuesta OK", "00", "Creacion exitosa");
                }else {
                    log.info("No se actualizo la categoria");
                    response.setMetadata("Respuesta No Actualizada", "-1", "Categoria no actualizada");
                    return new ResponseEntity<BitacoraResponseRest>(response, HttpStatus.BAD_REQUEST);
                }

            }else {
                log.info("No encontrada la categoria");
                response.setMetadata("Respuesta No Encontrada", "-1", "Categoria no localizada");
                return new ResponseEntity<BitacoraResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error", "-1", "Error al actualizar la categoria");
            log.error("Error al actualizar la categoria: ",e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<BitacoraResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<BitacoraResponseRest>(response, HttpStatus.OK);
    }
}
