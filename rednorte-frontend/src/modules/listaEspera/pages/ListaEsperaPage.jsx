import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { obtenerListaEspera } from "../services/listaEsperaService";
import { esAdmin, obtenerSesion } from "../../../services/authService";

const ListaEsperaPage = () => {
  const [listaEspera, setListaEspera] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  const admin = esAdmin();
  const session = obtenerSesion();

  useEffect(() => {
    cargarListaEspera();
  }, []);

  const cargarListaEspera = async () => {
    try {
      setLoading(true);
      setError(false);

      const data = await obtenerListaEspera();

      if (admin) {
        setListaEspera(data);
      } else {
        const listaPaciente = data.filter(
          (item) => item.paciente?.id === session?.pacienteId
        );

        setListaEspera(listaPaciente);
      }
    } catch (err) {
      console.error("Error al conectar con backend:", err);
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main>
      <div className="card">
        <h1>
          {admin ? "Lista de Espera" : "Mi Lista de Espera"}
        </h1>

        <p>
          {admin
            ? "Listado de solicitudes médicas registradas."
            : "Consulta tus solicitudes médicas registradas."}
        </p>

        {admin && (
          <div className="actions">
            <Link to="/lista-espera/registro" className="btn-primary">
              Registrar solicitud
            </Link>
          </div>
        )}

        {loading && <p>Cargando lista de espera...</p>}

        {error && (
          <p style={{ color: "red" }}>
            Error al conectar con el backend.
          </p>
        )}

        {!loading && !error && (
          <>
            {listaEspera.length === 0 ? (
              <p>
                {admin
                  ? "No hay solicitudes registradas."
                  : "No tienes solicitudes registradas actualmente."}
              </p>
            ) : (
              <ul className="waitlist-list">
                {listaEspera.map((item) => (
                  <li key={item.id} className="waitlist-item">
                    <div className="waitlist-header">
                      <span className="waitlist-paciente">
                        {item.paciente?.nombre} {item.paciente?.apellido}
                      </span>

                      <span
                        className={`badge-prioridad prioridad-${item.prioridad?.toLowerCase()}`}
                      >
                        {item.prioridad}
                      </span>
                    </div>

                    <span className="waitlist-meta">
                      Especialidad: {item.especialidad}
                    </span>

                    <span className="waitlist-meta">
                      Estado: {item.estado}
                    </span>

                    <span className="waitlist-meta">
                      Fecha ingreso: {item.fechaIngreso}
                    </span>
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

export default ListaEsperaPage;