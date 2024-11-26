const BitacoraSchema = new mongoose.Schema({
    usuario: { type: mongoose.Schema.Types.ObjectId, ref: 'Usuario', required: true },
    equipo: { type: mongoose.Schema.Types.ObjectId, ref: 'Equipo' },
    salon: { type: mongoose.Schema.Types.ObjectId, ref: 'Salon', required: true },
    horaEntrada: { type: Date, default: Date.now },
    horaSalida: { type: Date },
    comentarios: { type: String },
    profesor: { type: String },
});

