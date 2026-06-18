import { useEffect, useState } from "react";
import {
  obtenerReportes,
  obtenerNotificaciones,
} from "../services/reporteService";

const ReportesPage = () => {

  const [reportes, setReportes] = useState([]);
  const [notificaciones, setNotificaciones] = useState([]);

  const cargarDatos = async () => {

    try {

      const dataReportes = await obtenerReportes();
      const dataNotificaciones = await obtenerNotificaciones();

      setReportes(dataReportes);
      setNotificaciones(dataNotificaciones);

    } catch (error) {

      console.error("Error al cargar datos:", error);
      alert("Error al cargar reportes y notificaciones");

    }
  };

  useEffect(() => {
    cargarDatos();
  }, []);

  const totalReportes = reportes.length;

  const totalSolicitudes = reportes.reduce(
    (total, reporte) => total + (reporte.totalSolicitudes || 0),
    0
  );

  const totalCitas = reportes.reduce(
    (total, reporte) => total + (reporte.totalCitas || 0),
    0
  );

  return (
    <main>

      <div className="card">

        <h1>Reportes del Sistema</h1>

        <p>
          Panel administrativo con indicadores del sistema hospitalario RedNorte.
        </p>

        <div className="report-grid">

          <div className="report-card">

            <h2>Total Reportes</h2>

            <div className="report-number">
              {totalReportes}
            </div>

            <p className="report-description">
              Reportes registrados en el sistema.
            </p>

          </div>

          <div className="report-card">

            <h2>Total Solicitudes</h2>

            <div className="report-number">
              {totalSolicitudes}
            </div>

            <p className="report-description">
              Solicitudes procesadas en listas de espera.
            </p>

          </div>

          <div className="report-card">

            <h2>Total Citas</h2>

            <div className="report-number">
              {totalCitas}
            </div>

            <p className="report-description">
              Citas médicas registradas.
            </p>

          </div>

        </div>

        <div className="report-table-container">

          <table className="report-table">

            <thead>

              <tr>
                <th>ID</th>
                <th>Tipo</th>
                <th>Fecha</th>
                <th>Solicitudes</th>
                <th>Citas</th>
                <th>Tiempo Promedio</th>
              </tr>

            </thead>

            <tbody>

              {reportes.map((reporte) => (

                <tr key={reporte.id}>

                  <td>{reporte.id}</td>
                  <td>{reporte.tipoReporte}</td>
                  <td>{reporte.fechaReporte}</td>
                  <td>{reporte.totalSolicitudes}</td>
                  <td>{reporte.totalCitas}</td>
                  <td>{reporte.tiempoPromedioEspera} días</td>

                </tr>

              ))}

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

          {notificaciones.map((notificacion) => (

            <li
              key={notificacion.id}
              className="waitlist-item"
            >

              <div className="waitlist-header">

                <span className="waitlist-paciente">
                  {notificacion.tipo}
                </span>

                <span className="status-badge estado-asignada">
                  {notificacion.estadoEnvio}
                </span>

              </div>

              <span>
                {notificacion.mensaje}
              </span>

              <span className="waitlist-meta">
                Fecha: {notificacion.fechaEnvio}
              </span>

            </li>

          ))}

        </ul>

      </div>

    </main>
  );
};

export default ReportesPage;