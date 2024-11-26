const express = require('express');
const Usuario = require('../models/Usuario');
const Bitacora = require('../models/Bitacora');
const SoporteTecnico = require('../models/SoporteTecnico');
const AdminBitacora = require('../models/AdminBitacora');

const router = express.Router();

/** Rutas de Usuarios */

// Obtener todos los usuarios
router.get('/usuarios', async (req, res) => {
    try {
        const usuarios = await Usuario.findAll();
        res.json(usuarios);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Crear un usuario
router.post('/usuarios', async (req, res) => {
    try {
        const usuario = await Usuario.create(req.body);
        res.status(201).json(usuario);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});

// Actualizar un usuario
router.put('/usuarios/:id', async (req, res) => {
    try {
        const usuario = await Usuario.findByPk(req.params.id);
        if (!usuario) return res.status(404).json({ error: 'Usuario no encontrado' });

        await usuario.update(req.body);
        res.json(usuario);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Eliminar un usuario
router.delete('/usuarios/:id', async (req, res) => {
    try {
        const usuario = await Usuario.findByPk(req.params.id);
        if (!usuario) return res.status(404).json({ error: 'Usuario no encontrado' });

        await usuario.destroy();
        res.status(204).send();
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

/** Rutas de Bitácoras */

// Registrar una nueva bitácora
router.post('/bitacoras', async (req, res) => {
    try {
        const bitacora = await Bitacora.create(req.body);
        res.status(201).json(bitacora);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});

// Obtener todas las bitácoras
router.get('/bitacoras', async (req, res) => {
    try {
        const bitacoras = await Bitacora.findAll();
        res.json(bitacoras);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

/** Rutas de Soporte Técnico */

// Obtener todos los registros de soporte técnico
router.get('/soporte-tecnico', async (req, res) => {
    try {
        const registros = await SoporteTecnico.findAll();
        res.json(registros);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Registrar un nuevo soporte técnico
router.post('/soporte-tecnico', async (req, res) => {
    try {
        const registro = await SoporteTecnico.create(req.body);
        res.status(201).json(registro);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});

// Actualizar un registro de soporte técnico
router.put('/soporte-tecnico/:id', async (req, res) => {
    try {
        const registro = await SoporteTecnico.findByPk(req.params.id);
        if (!registro) return res.status(404).json({ error: 'Registro no encontrado' });

        await registro.update(req.body);
        res.json(registro);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Eliminar (lógicamente) un registro de soporte técnico
router.delete('/soporte-tecnico/:id', async (req, res) => {
    try {
        const registro = await SoporteTecnico.findByPk(req.params.id);
        if (!registro) return res.status(404).json({ error: 'Registro no encontrado' });

        await registro.update({ activo: false }); // Actualización lógica
        res.status(204).send();
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});


/** Rutas de Administrador de Bitácoras */

// Obtener todas las bitácoras asignadas al administrador
router.get('/admin-bitacoras', async (req, res) => {
    try {
        const registros = await AdminBitacora.findAll();
        res.json(registros);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Crear un nuevo registro de administrador para una bitácora
router.post('/admin-bitacoras', async (req, res) => {
    try {
        const registro = await AdminBitacora.create(req.body);
        res.status(201).json(registro);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});

// Actualizar el estado de una bitácora (por ejemplo, "resuelto", "pendiente", etc.)
router.put('/admin-bitacoras/:id', async (req, res) => {
    try {
        const registro = await AdminBitacora.findByPk(req.params.id);
        if (!registro) return res.status(404).json({ error: 'Registro no encontrado' });

        await registro.update(req.body);
        res.json(registro);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Eliminar (lógicamente) un registro de administrador
router.delete('/admin-bitacoras/:id', async (req, res) => {
    try {
        const registro = await AdminBitacora.findByPk(req.params.id);
        if (!registro) return res.status(404).json({ error: 'Registro no encontrado' });

        await registro.update({ activo: false }); // Eliminación lógica
        res.status(204).send();
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});


module.exports = router;
