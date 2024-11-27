package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Salon;
import com.company.bitacora.backend.model.dao.SalonDao;
import com.company.bitacora.backend.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalonServiceImpl implements SalonService {
    @Autowired
    private SalonDao salonDao;

    @Override
    public List<Salon> getSalones() {
        return (List<Salon>) salonDao.findAll();
    }

    @Override
    public Salon getSalonById(Long id) {
        Optional<Salon> salon = salonDao.findById(id);
        return salon.orElseThrow(() -> new RuntimeException("Salon no encontrado"));
    }

    @Override
    public Salon saveSalon(Salon salon) {
        return salonDao.save(salon);
    }

    @Override
    public Salon updateSalon(Long id, Salon salon) {
        Salon nuevoSalon = getSalonById(id);
        nuevoSalon.setNombre(salon.getNombre());
        nuevoSalon.setUbicacion(salon.getUbicacion());
        nuevoSalon.setCapacidad(salon.getCapacidad());
        return salonDao.save(nuevoSalon);
    }

    @Override
    public void deleteSalon(Long id) {
        salonDao.deleteById(id);
    }
}
