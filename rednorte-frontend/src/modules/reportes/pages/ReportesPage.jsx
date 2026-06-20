import { useEffect, useState } from "react";
import {
  obtenerPacientesReporte,
  obtenerListaEsperaReporte,
  obtenerCitasReporte,
  obtenerNotificaciones,
} from "../services/reporteService";

const ReportesPage = () => {
  const [pacientes, setPacientes] = useState([]);
  const [listaEspera, setListaEspera] = useState([]);
  const [citas, setCitas] = useState([]);
  const [notificaciones, setNotificaciones] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const cargarDatos = async () => {
    try {
      setLoading(true);
      setError("");

      const [
        dataPacientes,
        dataListaEspera,
        dataCitas,
        dataNotificaciones,
      ] = await Promise.all([
        obtenerPacientesReporte(),
        obtenerListaEsperaReporte(),
        obtenerCitasReporte(),
        obtenerNotificaciones(),
      ]);

      setPacientes(Array.isArray(dataPacientes) ? dataPacientes : []);
      setListaEspera(Array.isArray(dataListaEspera) ? dataListaEspera : []);
      setCitas(Array.isArray(dataCitas) ? dataCitas : []);
      setNotificaciones(
        Array.isArray(dataNotificaciones) ? dataNotificaciones : []
      );
    } catch (error) {
      console.error("Error al cargar reportes:", error);
      setError("No se pudieron cargar los indicadores del sistema.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    cargarDatos();
  }, []);

  const obtenerEstadoCita = (cita) => {
    return cita.estadoCita || cita.estado_cita || "";
  };

  const citasDisponibles = citas.filter(
    (cita) => obtenerEstadoCita(cita).toUpperCase() === "DISPONIBLE"
  );

  const citasReservadas = citas.filter(
    (cita) =>
      obtenerEstadoCita(cita).toUpperCase() === "RESERVADA" ||
      obtenerEstadoCita(cita).toUpperCase() === "ASIGNADA"
  );

  const solicitudesAlta = listaEspera.filter(
    (item) => item.prioridad?.toUpperCase() === "ALTA"
  );

  const solicitudesMedia = listaEspera.filter(
    (item) => item.prioridad?.toUpperCase() === "MEDIA"
  );

  const solicitudesBaja = listaEspera.filter(
    (item) => item.prioridad?.toUpperCase() === "BAJA"
  );

  const resumenEspecialidades = listaEspera.reduce((acumulador, item) => {
    const especialidad = item.especialidad || "Sin especialidad";

    if (!acumulador[especialidad]) {
      acumulador[especialidad] = {
        especialidad,
        solicitudes: 0,
        prioridadAlta: 0,
        prioridadMedia: 0,
        prioridadBaja: 0,
      };
    }

    acumulador[especialidad].solicitudes += 1;

    if (item.prioridad?.toUpperCase() === "ALTA") {
      acumulador[especialidad].prioridadAlta += 1;
    }

    if (item.prioridad?.toUpperCase() === "MEDIA") {
      acumulador[especialidad].prioridadMedia += 1;
    }

    if (item.prioridad?.toUpperCase() === "BAJA") {
      acumulador[especialidad].prioridadBaja += 1;
    }

    return acumulador;
  }, {});

  const tablaEspecialidades = Object.values(resumenEspecialidades);

  if (loading) {
    return (
      <main>
        <div className="card">
          <h1>Reportes del Sistema</h1>
          <p>Cargando indicadores del sistema...</p>
        </div>
      </main>
    );
  }

  return (
    <main>
      <div className="card">
        <h1>Reportes del Sistema</h1>

        <p>
          Panel administrativo con indicadores calculados desde los datos reales
          del sistema hospitalario RedNorte.
        </p>

        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        <div className="report-grid">
          <div className="report-card">
            <h2>Total Pacientes</h2>

            <div className="report-number">
              {pacientes.length}
            </div>

            <p className="report-description">
              Pacientes registrados en el sistema.
            </p>
          </div>

          <div className="report-card">
            <h2>Lista de Espera</h2>

            <div className="report-number">
              {listaEspera.length}
            </div>

            <p className="report-description">
              Solicitudes activas registradas en lista de espera.
            </p>
          </div>

          <div className="report-card">
            <h2>Total Citas</h2>

            <div className="report-number">
              {citas.length}
            </div>

            <p className="report-description">
              Citas médicas registradas en el sistema.
            </p>
          </div>

          <div className="report-card">
            <h2>Citas Disponibles</h2>

            <div className="report-number">
              {citasDisponibles.length}
            </div>

            <p className="report-description">
              Horas médicas disponibles para asignación.
            </p>
          </div>

          <div className="report-card">
            <h2>Citas Reservadas</h2>

            <div className="report-number">
              {citasReservadas.length}
            </div>

            <p className="report-description">
              Citas ya asignadas o reservadas.
            </p>
          </div>

          <div className="report-card">
            <h2>Prioridad Alta</h2>

            <div className="report-number">
              {solicitudesAlta.length}
            </div>

            <p className="report-description">
              Solicitudes críticas en lista de espera.
            </p>
          </div>
        </div>

        <h1 style={{ marginTop: "36px" }}>
          Resumen de solicitudes por prioridad
        </h1>

        <div className="report-grid">
          <div className="report-card">
            <h2>Alta</h2>
            <div className="report-number">{solicitudesAlta.length}</div>
            <p className="report-description">Atención prioritaria.</p>
          </div>

          <div className="report-card">
            <h2>Media</h2>
            <div className="report-number">{solicitudesMedia.length}</div>
            <p className="report-description">Atención pendiente.</p>
          </div>

          <div className="report-card">
            <h2>Baja</h2>
            <div className="report-number">{solicitudesBaja.length}</div>
            <p className="report-description">Atención no urgente.</p>
          </div>
        </div>

        <h1 style={{ marginTop: "36px" }}>
          Indicadores por especialidad
        </h1>

        <p>
          Resumen generado automáticamente a partir de las listas de espera.
        </p>

        <div className="report-table-container">
          <table className="report-table">
            <thead>
              <tr>
                <th>Especialidad</th>
                <th>Solicitudes</th>
                <th>Alta</th>
                <th>Media</th>
                <th>Baja</th>
              </tr>
            </thead>

            <tbody>
              {tablaEspecialidades.length > 0 ? (
                tablaEspecialidades.map((item) => (
                  <tr key={item.especialidad}>
                    <td>{item.especialidad}</td>
                    <td>{item.solicitudes}</td>
                    <td>{item.prioridadAlta}</td>
                    <td>{item.prioridadMedia}</td>
                    <td>{item.prioridadBaja}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5">
                    No existen solicitudes en lista de espera.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        <h1 style={{ marginTop: "36px" }}>
          Notificaciones recientes
        </h1>

        <p>
          Últimos eventos generados por el sistema de soporte.
        </p>

        <ul className="waitlist-list">
          {notificaciones.length > 0 ? (
            notificaciones.map((notificacion) => (
              <li
                key={notificacion.id}
                className="waitlist-item"
              >
                <div className="waitlist-header">
                  <span className="waitlist-paciente">
                    {notificacion.tipo || "Notificación"}
                  </span>

                  <span className="status-badge estado-asignada">
                    {notificacion.estadoEnvio || "Registrada"}
                  </span>
                </div>

                <span>
                  {notificacion.mensaje || "Sin mensaje registrado"}
                </span>

                <span className="waitlist-meta">
                  Fecha: {notificacion.fechaEnvio || "Sin fecha"}
                </span>
              </li>
            ))
          ) : (
            <li className="waitlist-item">
              No existen notificaciones recientes.
            </li>
          )}
        </ul>
      </div>
    </main>
  );
};

export default ReportesPage;