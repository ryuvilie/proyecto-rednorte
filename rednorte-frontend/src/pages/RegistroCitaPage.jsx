import { useEffect, useState } from "react";
import { crearCita } from "../services/citaService";
import { bffApiClient } from "../api/apiClient";

const RegistroCitaPage = () => {
  const [doctores, setDoctores] = useState([]);

  const [form, setForm] = useState({
    doctorId: "",
    fechaCita: "",
    horaCita: "",
    establecimiento: "",
  });

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

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const doctorSeleccionado = doctores.find(
      (d) => d.id === Number(form.doctorId)
    );

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
            required
          >
            <option value="">Seleccionar doctor</option>

            {doctores.map((doctor) => (
              <option key={doctor.id} value={doctor.id}>
                {doctor.nombre} {doctor.apellido} -{" "}
                {doctor.especialidad}
              </option>
            ))}
          </select>

          <input
            type="date"
            name="fechaCita"
            value={form.fechaCita}
            onChange={handleChange}
            required
          />

          <input
            type="time"
            name="horaCita"
            value={form.horaCita}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="establecimiento"
            placeholder="Establecimiento"
            value={form.establecimiento}
            onChange={handleChange}
            required
          />

          <button type="submit" className="btn-primary">
            Crear hora disponible
          </button>
        </form>
      </div>
    </main>
  );
};

export default RegistroCitaPage;