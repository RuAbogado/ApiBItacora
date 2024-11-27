package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Categoria;
import com.company.bitacora.backend.model.dao.ICategoriaDao;
import com.company.bitacora.backend.response.CategoriaResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

    private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    @Autowired
    private ICategoriaDao categoriaDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
        log.info("Inicio metodo buscarCategorias");
        CategoriaResponseRest response = new CategoriaResponseRest();
        try {
            List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
            response.getCategoriaResponse().setCategoria(categoria);
            response.setMetada("Respuesta OK", "00", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Error al consultar la lista de categorias");
            log.error("Error al consultar categorias: ",e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
        log.info("Inicio metodo buscarPorId");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();

        try {
            Optional<Categoria> categoria = categoriaDao.findById(id);
            if(categoria.isPresent()) {
                list.add(categoria.get());
                response.getCategoriaResponse().setCategoria(list);
                response.setMetada("Respuesta OK", "00", "Respuesta exitosa");
            }else {
                log.info("No encontrada la categoria");
                response.setMetada("Respuesta No Encontrada", "-1", "Categoria no encontrada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetada("Error No Encontrada", "-1", "Error al consultar por Id");
            log.error("Error al consultar por Id categorias: ",e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
        log.info("Inicio metodo crear (Categoria)");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();

        try {
            Categoria categoriaGuardar = categoriaDao.save(categoria);
            if(categoriaGuardar != null) {
                list.add(categoriaGuardar);
                response.getCategoriaResponse().setCategoria(list);
                response.setMetada("Respuesta OK", "00", "Creacion exitosa");
            }else {
                log.info("No encontrada la categoria");
                response.setMetada("Respuesta No Creada", "-1", "Categoria no creada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetada("Error", "-1", "Error al guardar la categoria");
            log.error("Error al guardar la categoria: ",e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
        log.info("Inicio metodo actualizar (Categoria)");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();

        try {
            Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
            if(categoriaBuscada.isPresent()) {
                categoriaBuscada.get().setNombre(categoria.getNombre());
                categoriaBuscada.get().setDescripcion(categoria.getDescripcion());

                Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());

                if(categoriaActualizar != null) {
                    list.add(categoriaActualizar);
                    response.getCategoriaResponse().setCategoria(list);
                    response.setMetada("Respuesta OK", "00", "Creacion exitosa");
                }else {
                    log.info("No se actualizo la categoria");
                    response.setMetada("Respuesta No Actualizada", "-1", "Categoria no actualizada");
                    return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
                }

            }else {
                log.info("No encontrada la categoria");
                response.setMetada("Respuesta No Encontrada", "-1", "Categoria no localizada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetada("Error", "-1", "Error al actualizar la categoria");
            log.error("Error al actualizar la categoria: ",e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
        log.info("Inicio metodo eliminar (Categoria)");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();

        try {
            categoriaDao.deleteById(id);
            response.setMetada("Respuesta OK", "00", "Eliminacion exitosa");
        } catch (Exception e) {
            response.setMetada("Error", "-1", "Error al eliminar categoria");
            log.error("Error al eliminar la categoria: ",e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }
}
