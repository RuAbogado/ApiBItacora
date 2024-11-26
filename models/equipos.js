const EquipoSchema = new mongoose.Schema({
    codigo: { type: String, unique: true, required: true },
    estado: { type: String, enum: ['funcional', 'en reparación', 'no funcional'], default: 'funcional' },
    descripcion: { type: String },
    salon: { type: mongoose.Schema.Types.ObjectId, ref: 'Salon', required: true },
});
