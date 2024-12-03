package com.company.bitacora.backend.model.dao;

import com.company.bitacora.backend.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BitacoraDaoC extends JpaRepository<Bitacora, Long> {

    @Query("select c from Bitacora c where c.maestro = :nombre")
    public Bitacora findByNombre(@Param("nombre") String nombre);

    // Para actualizar la foto, no es necesario usar una consulta JPQL, solo se usa save o saveAndFlush
    public Bitacora save(Bitacora foto);
}
