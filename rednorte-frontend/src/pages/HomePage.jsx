import { Link } from "react-router-dom";
import { obtenerSesion, esAdmin, esPaciente } from "../services/authService";

const HomePage = () => {
  const session = obtenerSesion();
  const admin = esAdmin();
  const paciente = esPaciente();

  return (
    <main>
      <section className="hero-home hospital-hero">
        <div className="hero-content">
          <span className="badge">Servicio de Salud RedNorte</span>

          <h1>Plataforma Inteligente de Gestión Hospitalaria</h1>

          <p>
            Sistema orientado a optimizar la gestión de listas de espera,
            pacientes y citas médicas dentro de la red asistencial RedNorte.
          </p>

          <div className="hero-actions">
            {!session && (
              <>
                <Link to="/login" className="btn-primary">
                  Iniciar sesión
                </Link>

                <Link to="/registro" className="btn-secondary">
                  Crear cuenta paciente
                </Link>
              </>
            )}

            {session && admin && (
              <>
                <Link to="/pacientes" className="btn-primary">
                  Gestionar pacientes
                </Link>

                <Link to="/lista-espera" className="btn-secondary">
                  Gestionar lista de espera
                </Link>

                <Link to="/citas" className="btn-secondary">
                  Gestionar citas
                </Link>
              </>
            )}

            {session && paciente && (
              <>
                <Link to="/lista-espera" className="btn-primary">
                  Ver mi lista de espera
                </Link>

                <Link to="/citas" className="btn-secondary">
                  Ver mis citas
                </Link>
              </>
            )}
          </div>
        </div>
      </section>

      <section className="home-info-grid">
        <div className="info-card">
          <h2>Atención organizada</h2>
          <p>
            Centraliza la información clínica para mejorar el seguimiento de
            pacientes y solicitudes médicas.
          </p>
        </div>

        <div className="info-card">
          <h2>Gestión eficiente</h2>
          <p>
            Apoya la asignación de citas y la priorización de pacientes en lista
            de espera.
          </p>
        </div>

        <div className="info-card">
          <h2>Arquitectura moderna</h2>
          <p>
            Integración mediante frontend, BFF, microservicios y base de datos
            persistente.
          </p>
        </div>
      </section>
    </main>
  );
};

export default HomePage;