const { DataTypes } = require('sequelize');
const sequelize = require('./database');
const Salon = require('./Salon');

const Equipo = sequelize.define('Equipo', {
    codigo: { type: DataTypes.STRING, unique: true, allowNull: false },
    estado: { 
        type: DataTypes.ENUM('funcional', 'en reparación', 'no funcional'),
        defaultValue: 'funcional'
    },
    descripcion: { type: DataTypes.STRING },
});

Equipo.belongsTo(Salon); // Relación con el salón
module.exports = Equipo;
