import { useEffect, useState } from "react";
import { crearCita } from "../services/citaService";
import { bffApiClient } from "../../../api/apiClient";
import { validarCampoObligatorio, validarFecha } from "../../../utils/validations";

const RegistroCitaPage = () => {
  const [doctores, setDoctores] = useState([]);

  const [form, setForm] = useState({
    doctorId: "",
    fechaCita: "",
    horaCita: "",
    establecimiento: "",
  });

  const [errores, setErrores] = useState({});

  useEffect(() => {
    cargarDoctores();
  }, []);

  const cargarDoctores = async () => {
    try {
      const data = await bffApiClient("/doctores");
      setDoctores(data);
    } catch (error) {
      console.error("Error cargando doctores:", error);
    }
  };

  const validarFormulario = () => {
    const nuevosErrores = {};

    if (!validarCampoObligatorio(form.doctorId)) {
      nuevosErrores.doctorId = "Debe seleccionar un doctor.";
    }

    if (!validarFecha(form.fechaCita)) {
      nuevosErrores.fechaCita = "La fecha de la cita es obligatoria.";
    }

    if (!validarCampoObligatorio(form.horaCita)) {
      nuevosErrores.horaCita = "La hora de la cita es obligatoria.";
    }

    if (!validarCampoObligatorio(form.establecimiento)) {
      nuevosErrores.establecimiento = "El establecimiento es obligatorio.";
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

    const doctorSeleccionado = doctores.find(
      (d) => d.id === Number(form.doctorId)
    );

    if (!doctorSeleccionado) {
      setErrores({
        ...errores,
        doctorId: "El doctor seleccionado no es válido.",
      });
      return;
    }

    const payload = {
      fechaCita: form.fechaCita,
      horaCita: form.horaCita,
      establecimiento: form.establecimiento,
      estadoCita: "DISPONIBLE",

      doctor: {
        id: doctorSeleccionado.id,
      },
    };

    try {
      await crearCita(payload);

      alert("Hora médica creada correctamente.");

      setForm({
        doctorId: "",
        fechaCita: "",
        horaCita: "",
        establecimiento: "",
      });

      setErrores({});
    } catch (error) {
      console.error("Error al crear hora:", error);
      alert("Error al crear hora médica.");
    }
  };

  return (
    <main>
      <div className="card">
        <h1>Crear Hora Médica</h1>

        <p>
          Registra disponibilidad médica para que los pacientes puedan reservar
          horas.
        </p>

        <form onSubmit={handleSubmit} className="form">
          <select
            name="doctorId"
            value={form.doctorId}
            onChange={handleChange}
            className={errores.doctorId ? "input-error" : ""}
          >
            <option value="">Seleccionar doctor</option>

            {doctores.map((doctor) => (
              <option key={doctor.id} value={doctor.id}>
                {doctor.nombre} {doctor.apellido} - {doctor.especialidad}
              </option>
            ))}
          </select>
          {errores.doctorId && (
            <small className="error-text">{errores.doctorId}</small>
          )}

          <input
            type="date"
            name="fechaCita"
            value={form.fechaCita}
            onChange={handleChange}
            className={errores.fechaCita ? "input-error" : ""}
          />
          {errores.fechaCita && (
            <small className="error-text">{errores.fechaCita}</small>
          )}

          <input
            type="time"
            name="horaCita"
            value={form.horaCita}
            onChange={handleChange}
            className={errores.horaCita ? "input-error" : ""}
          />
          {errores.horaCita && (
            <small className="error-text">{errores.horaCita}</small>
          )}

          <input
            type="text"
            name="establecimiento"
            placeholder="Establecimiento"
            value={form.establecimiento}
            onChange={handleChange}
            className={errores.establecimiento ? "input-error" : ""}
          />
          {errores.establecimiento && (
            <small className="error-text">{errores.establecimiento}</small>
          )}

          <button type="submit" className="btn-primary">
            Crear hora disponible
          </button>
        </form>
      </div>
    </main>
  );
};

export default RegistroCitaPage;