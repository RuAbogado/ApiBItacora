const UsuarioSchema = new mongoose.Schema({
    nombre: { type: String, required: true },
    matricula: { type: String, unique: true, required: true },
    correo: { type: String, unique: true },
    contrasena: { type: String, required: true }, // Si manejas autenticación
    rol: { type: String, enum: ['alumno', 'administrador'], default: 'alumno' },
});
