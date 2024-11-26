// models/SoporteTecnico.js
const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const SoporteTecnico = sequelize.define('SoporteTecnico', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
    },
    detalle: {
        type: DataTypes.TEXT,
        allowNull: false,
    },
    estado: {
        type: DataTypes.STRING,
        allowNull: false,
        defaultValue: 'pendiente', // Por ejemplo, estado inicial
    },
    fecha: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    activo: {
        type: DataTypes.BOOLEAN,
        defaultValue: true,
    },
}, {
    tableName: 'soporte_tecnico',
    timestamps: true,
});

module.exports = SoporteTecnico;
