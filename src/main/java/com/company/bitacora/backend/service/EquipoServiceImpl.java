package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Equipo;
import com.company.bitacora.backend.model.dao.EquipoDao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoDao equipoDao;

    @Override
    public List<Equipo> getEquipos() {
        return (List<Equipo>) equipoDao.findAll();
    }

    @Override
    public Equipo getEquipoById(Long id) {
        Optional<Equipo> equipo = equipoDao.findById(id);
        return equipo.orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }


    @Override
    public Equipo saveEquipo(Equipo equipo) {
        return equipoDao.save(equipo);
    }

    @Override
    public Equipo updateEquipo(Long id, Equipo equipo) {
        Equipo nuevoEquipo = getEquipoById(id);
        nuevoEquipo.setMarca(equipo.getMarca());
        nuevoEquipo.setModelo(equipo.getModelo());
        return equipoDao.save(nuevoEquipo);
    }


    @Override
    public boolean deleteEquipo(Long id) {
        if(equipoDao.existsById(id)){
            equipoDao.deleteById(id);
            return true;
        }
        return false;
    }
}
