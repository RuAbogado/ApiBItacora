const { DataTypes } = require('sequelize');
const sequelize = require('./database');
const Usuario = require('./Usuario');
const SoporteTecnico = require('./SoporteTecnico');
const Bitacora = require('./Bitacora');

const AdministradorBitacora = sequelize.define('AdministradorBitacora', {
    estado: { type: DataTypes.STRING, allowNull: false, defaultValue: 'pendiente' },
    comentarios: { type: DataTypes.STRING },
});

AdministradorBitacora.belongsTo(Usuario); // Relación con el administrador
AdministradorBitacora.belongsTo(SoporteTecnico); // Relación con soporte técnico
AdministradorBitacora.belongsTo(Bitacora); // Relación con bitácoras
module.exports = AdministradorBitacora;
