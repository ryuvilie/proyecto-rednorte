import { useEffect, useState } from "react";
import { obtenerCitasDoctor } from "../services/citaService";
import { obtenerSesion } from "../services/authService";

const DoctorDashboardPage = () => {
  const session = obtenerSesion();

  const [citas, setCitas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    cargarCitasDoctor();
  }, []);

  const cargarCitasDoctor = async () => {
    try {
      setLoading(true);
      setError(false);

      const data = await obtenerCitasDoctor(session.doctor?.id || session.doctorId);

      setCitas(data);
    } catch (err) {
      console.error("Error cargando citas del doctor:", err);
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main>
      <div className="card">
        <div className="page-header">
          <div>
            <h1>Panel del Doctor</h1>
            <p>Consulta tus citas médicas asignadas.</p>
          </div>
        </div>

        {loading && <p>Cargando agenda médica...</p>}

        {error && (
          <div className="alert-error">
            No se pudieron cargar las citas del doctor.
          </div>
        )}

        {!loading && !error && citas.length === 0 && (
          <p>No tienes citas asignadas actualmente.</p>
        )}

        {!loading && !error && citas.length > 0 && (
          <ul className="appointment-list">
            {citas.map((cita) => (
              <li key={cita.id} className="appointment-card">
                <div className="appointment-header">
                  <div>
                    <h2>
                      {cita.listaEspera?.paciente?.nombre}{" "}
                      {cita.listaEspera?.paciente?.apellido}
                    </h2>
                    <p>
                      {cita.listaEspera?.especialidad ||
                        cita.doctor?.especialidad ||
                        "Especialidad no registrada"}
                    </p>
                  </div>

                  <span
                    className={`status-badge estado-${cita.estadoCita?.toLowerCase()}`}
                  >
                    {cita.estadoCita}
                  </span>
                </div>

                <div className="appointment-details">
                  <span>
                    <strong>Fecha:</strong> {cita.fechaCita}
                  </span>
                  <span>
                    <strong>Hora:</strong> {cita.horaCita}
                  </span>
                  <span>
                    <strong>Paciente:</strong>{" "}
                    {cita.listaEspera?.paciente?.rut || "Sin RUT registrado"}
                  </span>
                  <span>
                    <strong>Establecimiento:</strong>{" "}
                    {cita.establecimiento}
                  </span>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
    </main>
  );
};

export default DoctorDashboardPage;