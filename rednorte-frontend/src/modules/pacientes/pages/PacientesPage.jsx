import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  obtenerPacientes,
  eliminarPaciente,
  limpiarDependenciasPaciente,
} from "../services/pacienteService";

const PacientesPage = () => {
  const [pacientes, setPacientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [busqueda, setBusqueda] = useState("");

  useEffect(() => {
    cargarPacientes();
  }, []);

  const cargarPacientes = async () => {
    try {
      setLoading(true);
      setError(false);

      const data = await obtenerPacientes();
      setPacientes(data);
    } catch (err) {
      console.error("Error al conectar con backend:", err);
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  const handleEliminar = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas eliminar este paciente?"
    );

    if (!confirmar) return;

    try {
      await eliminarPaciente(id);
      cargarPacientes();
    } catch (error) {
      console.error("Error al eliminar paciente:", error);
      alert("No se pudo eliminar el paciente.");
    }
  };

  const pacientesFiltrados = pacientes.filter((paciente) => {
  const texto = `
    ${paciente.nombre}
    ${paciente.apellido}
    ${paciente.rut}
  `.toLowerCase();

      return texto.includes(busqueda.toLowerCase());
    });

  const handleLimpiarDependencias = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas eliminar las citas y listas de espera asociadas a este paciente?"
    );

    if (!confirmar) return;

    try {
      await limpiarDependenciasPaciente(id);
      alert("Registros asociados eliminados correctamente.");
      cargarPacientes();
    } catch (error) {
      console.error("Error al limpiar registros asociados:", error);
      alert("No se pudieron eliminar los registros asociados.");
    }
  };

  return (
    <main>
      <div className="card">
        <h1>Pacientes RedNorte</h1>
        <p>Listado de pacientes desde backend.</p>

        <Link to="/pacientes/registro" className="btn-primary">
          Registrar paciente
        </Link>

        <div className="search-container">
          <input
            type="text"
            placeholder="Buscar por nombre o RUT..."
            value={busqueda}
            onChange={(e) => setBusqueda(e.target.value)}
            className="search-input"
          />
        </div>

        {loading && <p>Cargando pacientes...</p>}

        {error && (
          <p style={{ color: "red" }}>
            Error al conectar con el backend.
          </p>
        )}

        {!loading && !error && (
          <>
            {pacientes.length === 0 ? (
              <p>No hay pacientes registrados.</p>
            ) : (
              <ul className="patient-list">
                {pacientesFiltrados.map((paciente) => (
                  <li key={paciente.id} className="patient-item">
                    <span className="patient-info">
                      {paciente.nombre} {paciente.apellido} - {paciente.rut}
                    </span>

                    <div className="patient-actions">
                      <button
                        type="button"
                        onClick={() => handleLimpiarDependencias(paciente.id)}
                        className="btn-secondary"
                      >
                        Limpiar registros
                      </button>

                      <button
                        type="button"
                        onClick={() => handleEliminar(paciente.id)}
                        className="btn-danger"
                      >
                        Eliminar paciente
                      </button>
                    </div>
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

export default PacientesPage;