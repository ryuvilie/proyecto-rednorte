import { Navigate } from "react-router-dom";
import { obtenerSesion } from "../services/authService";

const ProtectedRoute = ({
  children,
  requireAdmin = false,
  requirePaciente = false,
  requireDoctor = false,
}) => {
  const session = obtenerSesion();

  if (!session) {
    return <Navigate to="/login" replace />;
  }

  if (requireAdmin && session.rol !== "ADMIN_CLINICA") {
    return <Navigate to="/" replace />;
  }

  if (requirePaciente && session.rol !== "PACIENTE") {
    return <Navigate to="/" replace />;
  }

  if (requireDoctor && session.rol !== "DOCTOR") {
    return <Navigate to="/" replace />;
  }

  return children;
};

export default ProtectedRoute;