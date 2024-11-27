package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Salon;

import java.util.List;

public interface SalonService {
    List<Salon> getSalones();

    Salon getSalonById(Long id);

    Salon saveSalon(Salon salon);

    Salon updateSalon(Long id, Salon salon);

    void deleteSalon(Long id);
}
