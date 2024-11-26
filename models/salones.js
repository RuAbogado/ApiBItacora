const SalonSchema = new mongoose.Schema({
    nombre: { type: String, required: true },
    ubicacion: { type: String },
    capacidad: { type: Number },
    equipos: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Equipo' }], // Relación opcional
});
