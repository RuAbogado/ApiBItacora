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
        return soporteTecnico.orElseThrow(()-> new RuntimeException("Tecnico no encontrado"));
    }

    @Override
    public SoporteTecnico saveSoporteTecnico(SoporteTecnico soporteTecnico) {
        return soporteTecnicoDao.save(soporteTecnico);
    }

    @Override
    public SoporteTecnico updateSoporteTecnico(Long id, SoporteTecnico soporteTecnico) {
        SoporteTecnico Tecnico = getSoporteTecnico(id);
        Tecnico.setNombre(soporteTecnico.getNombre());
        Tecnico.setApellido(soporteTecnico.getApellido());
        Tecnico.setCorreo(soporteTecnico.getCorreo());
        Tecnico.setContrasena(soporteTecnico.getContrasena());
        Tecnico.setTipo(soporteTecnico.getTipo());
        return soporteTecnicoDao.save(soporteTecnico);
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
