package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.model.dao.BitacoraDao;
import com.company.bitacora.backend.response.BitacoraResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    @Autowired
    private BitacoraDao bitacoraDao;

    @Override
    public List<Bitacora> buscarBitacoras() {
        return (List<Bitacora>) bitacoraDao.findAll();
    }

    @Override
    public Bitacora buscarPorID(Long id) {
        return bitacoraDao.findById(id).orElse(null);
    }

    @Override
    public Bitacora crearBitacora(Bitacora bitacora) {
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
