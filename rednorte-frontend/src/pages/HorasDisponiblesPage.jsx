import { useEffect, useState } from "react";
import {
  obtenerHorasDisponibles,
  reservarHora,
} from "../services/disponibilidadService";
import { obtenerSesion } from "../services/authService";

const HorasDisponiblesPage = () => {
  const session = obtenerSesion();

  const [horas, setHoras] = useState([]);
  const [loading, setLoading] = useState(true);
  const [mensaje, setMensaje] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      setLoading(true);
      setError("");
      setMensaje("");

      const horasData = await obtenerHorasDisponibles();

      setHoras(horasData);
    } catch (err) {
      console.error(err);
      setError("No se pudieron cargar las horas disponibles.");
    } finally {
      setLoading(false);
    }
  };

  const handleReservar = async (citaId) => {
    try {
      setError("");
      setMensaje("");

      await reservarHora(citaId, session.pacienteId);

      setMensaje("Hora reservada correctamente.");
      await cargarDatos();
    } catch (err) {
      console.error(err);
      setError("No se pudo reservar la hora seleccionada.");
    }
  };

  return (
    <main>
      <div className="card">
        <div className="page-header">
          <div>
            <h1>Horas Disponibles</h1>

            <p>
              Revisa las horas médicas disponibles y reserva una atención médica.
            </p>
          </div>
        </div>

        {loading && <p>Cargando horas disponibles...</p>}

        {error && <div className="alert-error">{error}</div>}

        {mensaje && <div className="alert-success">{mensaje}</div>}

        {!loading && horas.length === 0 && (
          <p>No hay horas disponibles actualmente.</p>
        )}

        {!loading && horas.length > 0 && (
          <ul className="appointment-list">
            {horas.map((hora) => (
              <li key={hora.id} className="appointment-card">
                <div className="appointment-header">
                  <div>
                    <h2>
                      {hora.doctor
                        ? `${hora.doctor.nombre} ${hora.doctor.apellido}`
                        : "Doctor no asignado"}
                    </h2>

                    <p>
                      {hora.doctor?.especialidad ||
                        "Especialidad no registrada"}
                    </p>
                  </div>

                  <span className="status-badge estado-asignada">
                    {hora.estadoCita}
                  </span>
                </div>

                <div className="appointment-details">
                  <span>
                    <strong>Fecha:</strong> {hora.fechaCita}
                  </span>

                  <span>
                    <strong>Hora:</strong> {hora.horaCita}
                  </span>

                  <span>
                    <strong>Establecimiento:</strong>{" "}
                    {hora.establecimiento}
                  </span>
                </div>

                <div className="actions">
                  <button
                    className="btn-primary"
                    onClick={() => handleReservar(hora.id)}
                  >
                    Reservar hora
                  </button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
    </main>
  );
};

export default HorasDisponiblesPage;