import { NavLink, useNavigate } from "react-router-dom";
import {
  cerrarSesion,
  obtenerSesion,
  esAdmin,
  esDoctor,
} from "../services/authService";

import {
  Home,
  Users,
  Calendar,
  ClipboardList,
  FileBarChart2,
  UserRound,
  LogOut,
  LogIn,
} from "lucide-react";

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

        <NavLink to="/">
          <Home size={18} />
          <span>Inicio</span>
        </NavLink>

        {session && admin && (
          <>
            <NavLink to="/pacientes">
              <Users size={18} />
              <span>Pacientes</span>
            </NavLink>

            <NavLink to="/lista-espera">
              <ClipboardList size={18} />
              <span>Lista de Espera</span>
            </NavLink>

            <NavLink to="/citas">
              <Calendar size={18} />
              <span>Citas</span>
            </NavLink>
          </>
        )}

        {session && (admin || doctor) && (
          <NavLink to="/reportes">
            <FileBarChart2 size={18} />
            <span>Reportes</span>
          </NavLink>
        )}

        {session && doctor && (
          <NavLink to="/doctor">
            <UserRound size={18} />
            <span>Panel Doctor</span>
          </NavLink>
        )}

        {session && !admin && !doctor && (
          <>
            <NavLink to="/lista-espera">
              <ClipboardList size={18} />
              <span>Mi Lista de Espera</span>
            </NavLink>

            <NavLink to="/horas-disponibles">
              <Calendar size={18} />
              <span>Horas Disponibles</span>
            </NavLink>

            <NavLink to="/citas">
              <Calendar size={18} />
              <span>Mis Citas</span>
            </NavLink>
          </>
        )}

        {!session && (
          <NavLink to="/login">
            <LogIn size={18} />
            <span>Ingresar</span>
          </NavLink>
        )}

        {session && (
          <button
            className="logout-button"
            onClick={handleLogout}
          >
            <LogOut size={18} />
            <span>Cerrar sesión</span>
          </button>
        )}

      </div>
    </nav>
  );
};

export default Navbar;