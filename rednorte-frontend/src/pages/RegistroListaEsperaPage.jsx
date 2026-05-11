import { useState, useEffect } from "react";
import { obtenerPacientes } from "../services/pacienteService";
import { crearListaEspera } from "../services/listaEsperaService";

const RegistroListaEsperaPage = () => {
  const [pacientes, setPacientes] = useState([]);

  const [form, setForm] = useState({
    especialidad: "",
    prioridad: "",
    estado: "Pendiente",
    fechaIngreso: "",
    pacienteId: "",
  });

  useEffect(() => {
    cargarPacientes();
  }, []);

  const cargarPacientes = async () => {
    try {
      const data = await obtenerPacientes();
      setPacientes(data);
    } catch (error) {
      console.error("Error cargando pacientes:", error);
    }
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      especialidad: form.especialidad,
      prioridad: form.prioridad,
      estado: form.estado,
      fechaIngreso: form.fechaIngreso,
      paciente: {
        id: form.pacienteId,
      },
    };

    try {
      await crearListaEspera(payload);

      alert("Solicitud creada correctamente");

      setForm({
        especialidad: "",
        prioridad: "",
        estado: "Pendiente",
        fechaIngreso: "",
        pacienteId: "",
      });
    } catch (error) {
      console.error("Error al crear solicitud:", error);
      alert("Error al crear solicitud");
    }
  };

  return (
    <main>
      <div className="card">
        <h1>Registrar en Lista de Espera</h1>

        <form onSubmit={handleSubmit} className="form">
          <select
            name="pacienteId"
            value={form.pacienteId}
            onChange={handleChange}
            required
          >
            <option value="">Seleccionar paciente</option>
            {pacientes.map((p) => (
              <option key={p.id} value={p.id}>
                {p.nombre} {p.apellido}
              </option>
            ))}
          </select>

          <input
            type="text"
            name="especialidad"
            placeholder="Especialidad"
            value={form.especialidad}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="prioridad"
            placeholder="Prioridad (Alta, Media, Baja)"
            value={form.prioridad}
            onChange={handleChange}
            required
          />

          <input
            type="date"
            name="fechaIngreso"
            value={form.fechaIngreso}
            onChange={handleChange}
            required
          />

          <button type="submit" className="btn-primary">
            Registrar
          </button>
        </form>
      </div>
    </main>
  );
};

export default RegistroListaEsperaPage;