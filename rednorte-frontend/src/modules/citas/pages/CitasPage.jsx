import { useEffect, useState } from "react";
import { obtenerCitas, eliminarCita } from "../services/citaService";
import { Link } from "react-router-dom";
import { esAdmin, obtenerSesion } from "../../../services/authService";

const CitasPage = () => {
  const [citas, setCitas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  const admin = esAdmin();
  const session = obtenerSesion();

  useEffect(() => {
    cargarCitas();
  }, []);

  const cargarCitas = async () => {
    try {
      setLoading(true);
      setError(false);

      const data = await obtenerCitas();

      if (admin) {
        setCitas(data);
      } else {
        const citasPaciente = data.filter(
          (cita) => cita.listaEspera?.paciente?.id === session?.pacienteId
        );

        setCitas(citasPaciente);
      }
    } catch (err) {
      console.error("Error al conectar con backend:", err);
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  const handleEliminar = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas eliminar esta cita médica?"
    );

    if (!confirmar) return;

    try {
      await eliminarCita(id);

      setCitas((prev) => prev.filter((cita) => cita.id !== id));
    } catch (err) {
      console.error("Error eliminando cita:", err);
      alert("No se pudo eliminar la cita.");
    }
  };

  return (
    <main>
      <div className="card">
        <div className="page-header">
          <div>
            <h1>{admin ? "Citas Médicas" : "Mis Citas Médicas"}</h1>
            <p>
              {admin
                ? "Listado de citas médicas registradas en el sistema."
                : "Consulta tus citas médicas asignadas."}
            </p>
          </div>

          {admin && (
            <Link to="/citas/registro" className="btn-primary">
              Registrar cita
            </Link>
          )}
        </div>

        {loading && <p>Cargando citas...</p>}

        {error && (
          <p style={{ color: "red" }}>Error al conectar con el backend.</p>
        )}

        {!loading && !error && (
          <>
            {citas.length === 0 ? (
              <p>
                {admin
                  ? "No hay citas registradas."
                  : "No tienes citas médicas asignadas actualmente."}
              </p>
            ) : (
              <ul className="appointment-list">
                {citas.map((cita) => (
                  <li key={cita.id} className="appointment-card">
                    <div className="appointment-header">
                      <div>
                        <h2>
                          {cita.listaEspera?.paciente
                            ? `${cita.listaEspera.paciente.nombre} ${cita.listaEspera.paciente.apellido}`
                            : "Hora disponible"}
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
                        <strong>Médico:</strong>{" "}
                        {cita.doctor
                          ? `${cita.doctor.nombre} ${cita.doctor.apellido}`
                          : "Doctor no asignado"}
                      </span>

                      <span>
                        <strong>Especialidad:</strong>{" "}
                        {cita.doctor?.especialidad ||
                          cita.listaEspera?.especialidad ||
                          "No registrada"}
                      </span>

                      <span>
                        <strong>Establecimiento:</strong>{" "}
                        {cita.establecimiento}
                      </span>
                    </div>

                    {admin && (
                      <div className="actions">
                        <button
                          className="btn-danger"
                          onClick={() => handleEliminar(cita.id)}
                        >
                          Eliminar cita
                        </button>
                      </div>
                    )}
                  </li>
                ))}
              </ul>
            )}
          </>
        )}
      </div>
    </main>
  );
};

export default CitasPage;