import { NavLink, useNavigate } from "react-router-dom";
import {
  cerrarSesion,
  obtenerSesion,
  esAdmin,
  esDoctor,
} from "../services/authService";

const Navbar = () => {
  const navigate = useNavigate();
  const session = obtenerSesion();
  const admin = esAdmin();
  const doctor = esDoctor();

  const handleLogout = () => {
    cerrarSesion();
    navigate("/login");
    window.location.reload();
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <div className="navbar-icon">+</div>

        <div>
          <h2 className="navbar-logo">RedNorte</h2>
          <span className="navbar-subtitle">
            Gestión Hospitalaria
          </span>
        </div>
      </div>

      <div className="navbar-links">

        <NavLink to="/">Inicio</NavLink>

        {session && admin && (
          <>
            <NavLink to="/pacientes">Pacientes</NavLink>

            <NavLink to="/lista-espera">
              Lista de Espera
            </NavLink>

            <NavLink to="/citas">Citas</NavLink>

            <NavLink to="/reportes">
              Reportes
            </NavLink>
          </>
        )}
        {session && (admin || doctor) && (
        <NavLink to="/reportes">
          Reportes
        </NavLink>
      )}

        {session && doctor && (
          <>
            <NavLink to="/doctor">
              Panel Doctor
            </NavLink>
          </>
        )}

        {session && !admin && !doctor && (
          <>
            <NavLink to="/lista-espera">
              Mi Lista de Espera
            </NavLink>

            <NavLink to="/horas-disponibles">
              Horas disponibles
            </NavLink>

            <NavLink to="/citas">
              Mis Citas
            </NavLink>
          </>
        )}

        {!session && (
          <NavLink to="/login">
            Ingresar
          </NavLink>
        )}

        {session && (
          <button
            className="logout-button"
            onClick={handleLogout}
          >
            Cerrar sesión
          </button>
        )}

      </div>
    </nav>
  );
};

export default Navbar;