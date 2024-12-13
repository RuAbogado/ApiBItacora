package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Equipo;
import com.company.bitacora.backend.model.Salon;
import com.company.bitacora.backend.model.dao.BitacoraDao;
import com.company.bitacora.backend.model.dao.EquipoDao;
import com.company.bitacora.backend.service.SalonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoDao equipoDao;

    @Autowired
    private SalonService salonService; // Servicio para buscar salones

    @Override
    public List<Equipo> getEquipos() {
        return (List<Equipo>) equipoDao.findAll();
    }

    @Override
    public Equipo getEquipoById(Long id) {
        return equipoDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + id));
    }

    @Override
    public Equipo saveEquipo(Equipo equipo) {
        // Validar que el salón exista antes de guardar el equipo
        Long salonId = equipo.getSalon().getId();
        Salon salon = salonService.getSalonById(salonId);
        if (salon == null) {
            throw new EntityNotFoundException("Salón no encontrado con ID: " + salonId);
        }

        // Asociar el salón al equipo y guardar
        equipo.setSalon(salon);
        return equipoDao.save(equipo);
    }

    @Override
    public Equipo updateEquipo(Long id, Equipo equipo) {
        // Obtener el equipo existente
        Equipo equipoExistente = getEquipoById(id);

        // Actualizar los campos básicos del equipo
        equipoExistente.setMarca(equipo.getMarca());
        equipoExistente.setModelo(equipo.getModelo());
        equipoExistente.setNumeroSerie(equipo.getNumeroSerie());

        // Validar que el salón existe antes de asociarlo
        Long salonId = equipo.getSalon().getId();
        Salon salon = salonService.getSalonById(salonId);
        if (salon == null) {
            throw new EntityNotFoundException("Salón no encontrado con ID: " + salonId);
        }

        // Asociar el salón actualizado
        equipoExistente.setSalon(salon);

        // Guardar el equipo actualizado
        return equipoDao.save(equipoExistente);
    }

    @Override
    public boolean deleteEquipo(Long id) {
        if (equipoDao.existsById(id)) {
            // Eliminar registros relacionados en la tabla `bitacora`
            BitacoraDao.deleteByEquipoId(id); // Asegúrate de tener este método en tu `BitacoraDao`

            // Eliminar el equipo
            equipoDao.deleteById(id);
            return true;
        }
        return false;
    }

}
