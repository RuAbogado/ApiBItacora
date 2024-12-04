package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.SoporteTecnico_Bitacora;
import com.company.bitacora.backend.model.dao.BitacoraDao;
import com.company.bitacora.backend.model.dao.SoporteTecnico_BitacoraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoporteTecnico_BitacoraServiceImpl implements SoporteTecnico_BitacoraService {

    @Autowired
    private SoporteTecnico_BitacoraDao bitacora2Dao;
    @Autowired
    private BitacoraDao bitacoraDao;

    @Override
    public List<SoporteTecnico_Bitacora> BitacorasTecnicos() {
        return (List<SoporteTecnico_Bitacora>) bitacora2Dao.findAll();
    }

    @Override
    public SoporteTecnico_Bitacora BuscarBitacora(Long id) {
        Optional<SoporteTecnico_Bitacora> soporteTecnicoBitacora = bitacora2Dao.findById(id);
        return soporteTecnicoBitacora.orElseThrow(()-> new RuntimeException("Bitacora inexistente"));
    }

    @Override
    public SoporteTecnico_Bitacora NuevaBitacora(SoporteTecnico_Bitacora SoporteTecnico_Bitacora) {
        return bitacora2Dao.save(SoporteTecnico_Bitacora);
    }

    @Override
    public SoporteTecnico_Bitacora actualizarBitacora(Long id, SoporteTecnico_Bitacora SoporteTecnico_Bitacora) {
        SoporteTecnico_Bitacora Bitacora2 = BuscarBitacora(id);
        Bitacora2.setFecha_Atencion(SoporteTecnico_Bitacora.getFecha_Atencion());
        Bitacora2.setObservaciones(SoporteTecnico_Bitacora.getObservaciones());
        return bitacora2Dao.save(Bitacora2);
    }


    @Override
    public boolean eliminarBitacora(Long id) {
        if (bitacora2Dao.existsById(id)) {
            bitacora2Dao.deleteById(id);
            return true;
        }
        return false;
    }
}
