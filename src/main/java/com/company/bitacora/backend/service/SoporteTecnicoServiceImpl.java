package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Authority;
import com.company.bitacora.backend.model.SoporteTecnico;
import com.company.bitacora.backend.model.User;
import com.company.bitacora.backend.model.dao.AuthorityDao;
import com.company.bitacora.backend.model.dao.SoporteTecnicoDao;
import com.company.bitacora.backend.model.dao.UserAuthoritiesDao;
import com.company.bitacora.backend.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SoporteTecnicoServiceImpl implements SoporteTecnicoService {

    @Autowired
    private SoporteTecnicoDao soporteTecnicoDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private UserAuthoritiesDao userAuthoritiesDao;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<SoporteTecnico> listarTecnicos() {
        return (List<SoporteTecnico>) soporteTecnicoDao.findAll();
    }

    @Override
    public SoporteTecnico getSoporteTecnico(Long id) {
        Optional<SoporteTecnico> soporteTecnico = soporteTecnicoDao.findById(id);
        return soporteTecnico.orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
    }

    @Transactional
    @Override
    public SoporteTecnico saveSoporteTecnico(SoporteTecnico soporteTecnico) {
        // Si el soporte técnico tiene un id previamente asignado, significa que ya existe
        if (soporteTecnico.getId() != null && soporteTecnicoDao.existsById(soporteTecnico.getId())) {
            throw new IllegalArgumentException("Ya existe un técnico con el id: " + soporteTecnico.getId());
        }

//        // Encriptar contraseña
//        String encryptedPassword = passwordEncoder.encode(soporteTecnico.getContrasena());
//        soporteTecnico.setContrasena(encryptedPassword);

        // Guardar técnico en la tabla SoporteTecnico
        SoporteTecnico savedSoporteTecnico = soporteTecnicoDao.save(soporteTecnico);

        // Crear y guardar usuario en la tabla `users`
        User user = new User();
        user.setUsername(soporteTecnico.getCorreo());
        user.setPassword(soporteTecnico.getContrasena()); // Guardar la contraseña encriptada
        user.setEnabled(true);
        userDao.save(user);

        // Asociar o crear rol 'ROLE_TECNICO'
        Authority authority = authorityDao.findByAuthority("ROLE_TECNICO");
        if (authority == null) {
            authority = new Authority();
            authority.setAuthority("ROLE_TECNICO");
            authorityDao.save(authority);
        }

        // Relacionar usuario y rol
        authority.getUsers().add(user);
        user.getAuthorities().add(authority);

        authorityDao.save(authority);
        userDao.save(user);

        return savedSoporteTecnico;
    }

    @Transactional
    @Override
    public SoporteTecnico updateSoporteTecnico(String correo, SoporteTecnico soporteTecnico) {
        SoporteTecnico existingSoporteTecnico = soporteTecnicoDao.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        // Actualizar campos
        existingSoporteTecnico.setNombre(soporteTecnico.getNombre());
        existingSoporteTecnico.setApellido(soporteTecnico.getApellido());
        existingSoporteTecnico.setCorreo(soporteTecnico.getCorreo());
        if (soporteTecnico.getContrasena() != null && !soporteTecnico.getContrasena().isEmpty()) {
            existingSoporteTecnico.setContrasena(soporteTecnico.getContrasena()); // No encriptar
        }
        existingSoporteTecnico.setTipo(soporteTecnico.getTipo());

        return soporteTecnicoDao.save(existingSoporteTecnico);
    }

    @Transactional
    @Override
    public boolean deleteSoporteTecnico(String correo) {
        SoporteTecnico soporteTecnico = soporteTecnicoDao.findByCorreo(correo).orElse(null);
        if (soporteTecnico == null) {
            return false; // El técnico no fue encontrado
        }

        // Eliminar los registros relacionados en user_authorities
        User user = userDao.findByUsername(soporteTecnico.getCorreo()).orElse(null);
        if (user != null) {
            // Eliminar registros en user_authorities primero (si los hay)
            userAuthoritiesDao.deleteByUserId(user.getId());

            // Eliminar usuario
            userDao.delete(user);
        }

        // Eliminar el técnico
        soporteTecnicoDao.delete(soporteTecnico);

        return true;
    }

}