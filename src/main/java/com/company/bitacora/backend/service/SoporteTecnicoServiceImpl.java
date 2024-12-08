package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.SoporteTecnico;
import com.company.bitacora.backend.model.dao.SoporteTecnicoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoporteTecnicoServiceImpl implements SoporteTecnicoService {

    @Autowired
    private SoporteTecnicoDao soporteTecnicoDao;

    @Override
    public List<SoporteTecnico> listarTecnicos() {
        return (List<SoporteTecnico>) soporteTecnicoDao.findAll();
    }

    @Override
    public SoporteTecnico getSoporteTecnico(Long id) {
        Optional<SoporteTecnico> soporteTecnico = soporteTecnicoDao.findById(id);
        return soporteTecnico.orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
    }

    @Override
    public SoporteTecnico saveSoporteTecnico(SoporteTecnico soporteTecnico) {
        return soporteTecnicoDao.save(soporteTecnico);
    }

    @Override
    public SoporteTecnico updateSoporteTecnico(Long id, SoporteTecnico soporteTecnico) {
        // Obtén el técnico existente en base al ID
        SoporteTecnico tecnicoExistente = getSoporteTecnico(id);

        // Actualiza los campos con los valores proporcionados
        tecnicoExistente.setNombre(soporteTecnico.getNombre());
        tecnicoExistente.setApellido(soporteTecnico.getApellido());
        tecnicoExistente.setCorreo(soporteTecnico.getCorreo());
        tecnicoExistente.setContrasena(soporteTecnico.getContrasena());
        tecnicoExistente.setTipo(soporteTecnico.getTipo());

        // Guarda el técnico actualizado y devuélvelo
        return soporteTecnicoDao.save(tecnicoExistente);  // Aquí guardamos el técnico actualizado
    }

    @Override
    public boolean deleteSoporteTecnico(Long id) {
        if (soporteTecnicoDao.existsById(id)) {
            soporteTecnicoDao.deleteById(id);
            return true;
        }
        return false;
    }
}
