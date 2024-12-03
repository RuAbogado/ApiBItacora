package com.company.bitacora.backend.response;

import com.company.bitacora.backend.model.Salon;

import java.util.List;

public class SalonResponse {
    private List<Salon> salon;

    public List<Salon> getSalon() {
        return salon;
    }

    public void setSalon(List<Salon> salon) {
        this.salon = salon;
    }
}
