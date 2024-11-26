const { DataTypes } = require('sequelize');
const sequelize = require('./database');

const Usuario = sequelize.define('Usuario', {
    nombre: { type: DataTypes.STRING, allowNull: false },
    matricula: { type: DataTypes.STRING, unique: true, allowNull: false },
    correo: { type: DataTypes.STRING, unique: true },
    contrasena: { type: DataTypes.STRING, allowNull: false },
    rol: { type: DataTypes.ENUM('alumno', 'administrador'), defaultValue: 'alumno' },
});

module.exports = Usuario;
