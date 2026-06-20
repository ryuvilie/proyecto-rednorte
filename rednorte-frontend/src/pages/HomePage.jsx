import { useState } from "react";
import { Link } from "react-router-dom";
import { obtenerSesion, esAdmin, esPaciente } from "../services/authService";

const HomePage = () => {
  const session = obtenerSesion();
  const admin = esAdmin();
  const paciente = esPaciente();

  const [mostrarEasterEgg, setMostrarEasterEgg] = useState(false);

  return (
    <main>
      <section className="hero-home hospital-hero">
        <div className="hero-content">
          <span className="badge">Rednorte</span>

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

          <div className="hero-features">
            <span>Gestión de Pacientes</span>
            <span>Gestión de Citas</span>
            <span>Lista de Espera</span>
          </div>
        </div>
      </section>

      <section className="home-info-grid">
        <div className="info-card">
          <h2>Gestión de Pacientes</h2>
          <p>
            Registro y administración centralizada de pacientes para facilitar el
            seguimiento clínico y mejorar la continuidad de atención.
          </p>
        </div>

        <div className="info-card">
          <h2>Administración de Citas</h2>
          <p>
            Gestión de disponibilidad médica, reservas y control de citas para
            una atención más eficiente y organizada.
          </p>
        </div>

        <div className="info-card">
          <h2>Lista de Espera Inteligente</h2>
          <p>
            Priorización y gestión de pacientes en espera para optimizar recursos
            y reducir tiempos de atención.
          </p>
        </div>
      </section>

      {session && admin && (
        <>
          <button
            className="easter-egg-button"
            onClick={() => setMostrarEasterEgg(true)}
            title="RedNorte"
          >
            •
          </button>

          {mostrarEasterEgg && (
            <div
              className="easter-egg-overlay"
              onClick={() => setMostrarEasterEgg(false)}
            >
              <img
                src="/easter-egg/profesor.jpeg"
                alt="Easter egg RedNorte"
                className="easter-egg-image"
              />
            </div>
          )}
        </>
      )}
    </main>
  );
};

export default HomePage;