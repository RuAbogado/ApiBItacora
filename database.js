const { Sequelize } = require('sequelize');


const sequelize = new Sequelize('bitacora', 'root', 'contraseña', {
    host: 'localhost',
    dialect: 'mysql',
    logging: false, 
});

sequelize
    .authenticate()
    .then(() => console.log('Conexión a MySQL exitosa.'))
    .catch((err) => console.error('Error al conectar a MySQL:', err));

module.exports = sequelize;
