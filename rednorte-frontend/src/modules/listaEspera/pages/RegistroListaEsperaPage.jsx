import { useState, useEffect } from "react";
import { obtenerPacientes } from "../../pacientes/services/pacienteService";
import { crearListaEspera } from "../services/listaEsperaService";
import {
  validarCampoObligatorio,
  validarFecha,
} from "../../../utils/validations";

const RegistroListaEsperaPage = () => {
  const [pacientes, setPacientes] = useState([]);

  const [form, setForm] = useState({
    especialidad: "",
    prioridad: "",
    estado: "Pendiente",
    fechaIngreso: "",
    pacienteId: "",
  });

  const [errores, setErrores] = useState({});

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

  const validarFormulario = () => {
    const nuevosErrores = {};

    if (!validarCampoObligatorio(form.pacienteId)) {
      nuevosErrores.pacienteId = "Debe seleccionar un paciente.";
    }

    if (!validarCampoObligatorio(form.especialidad)) {
      nuevosErrores.especialidad = "La especialidad es obligatoria.";
    }

    if (!validarCampoObligatorio(form.prioridad)) {
      nuevosErrores.prioridad = "La prioridad es obligatoria.";
    }

    if (!validarFecha(form.fechaIngreso)) {
      nuevosErrores.fechaIngreso = "La fecha de ingreso es obligatoria.";
    }

    setErrores(nuevosErrores);

    return Object.keys(nuevosErrores).length === 0;
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });

    setErrores({
      ...errores,
      [e.target.name]: "",
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validarFormulario()) {
      return;
    }

    const payload = {
      especialidad: form.especialidad,
      prioridad: form.prioridad,
      estado: form.estado,
      fechaIngreso: form.fechaIngreso,
      paciente: {
        id: Number(form.pacienteId),
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

      setErrores({});
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
            className={errores.pacienteId ? "input-error" : ""}
          >
            <option value="">Seleccionar paciente</option>
            {pacientes.map((p) => (
              <option key={p.id} value={p.id}>
                {p.nombre} {p.apellido}
              </option>
            ))}
          </select>
          {errores.pacienteId && (
            <small className="error-text">{errores.pacienteId}</small>
          )}

          <input
            type="text"
            name="especialidad"
            placeholder="Especialidad"
            value={form.especialidad}
            onChange={handleChange}
            className={errores.especialidad ? "input-error" : ""}
          />
          {errores.especialidad && (
            <small className="error-text">{errores.especialidad}</small>
          )}

          <input
            type="text"
            name="prioridad"
            placeholder="Prioridad (Alta, Media, Baja)"
            value={form.prioridad}
            onChange={handleChange}
            className={errores.prioridad ? "input-error" : ""}
          />
          {errores.prioridad && (
            <small className="error-text">{errores.prioridad}</small>
          )}

          <input
            type="date"
            name="fechaIngreso"
            value={form.fechaIngreso}
            onChange={handleChange}
            className={errores.fechaIngreso ? "input-error" : ""}
          />
          {errores.fechaIngreso && (
            <small className="error-text">{errores.fechaIngreso}</small>
          )}

          <button type="submit" className="btn-primary">
            Registrar
          </button>
        </form>
      </div>
    </main>
  );
};

export default RegistroListaEsperaPage;