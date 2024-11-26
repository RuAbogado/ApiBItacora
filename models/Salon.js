const { DataTypes } = require('sequelize');
const sequelize = require('./database');

const Salon = sequelize.define('Salon', {
    nombre: { type: DataTypes.STRING, allowNull: false },
    ubicacion: { type: DataTypes.STRING },
    capacidad: { type: DataTypes.INTEGER },
});

module.exports = Salon;
