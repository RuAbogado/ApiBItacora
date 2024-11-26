// models/Bitacora.js
const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const Bitacora = sequelize.define('Bitacora', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
    },
    descripcion: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    fecha: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    usuarioId: {
        type: DataTypes.INTEGER,
        allowNull: false,
    },
}, {
    tableName: 'bitacoras',
    timestamps: true, // Si tienes columnas createdAt y updatedAt
});

module.exports = Bitacora;
