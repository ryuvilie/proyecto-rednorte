import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../services/authService";

const LoginPage = () => {
  const navigate = useNavigate();

  const [correo, setCorreo] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [cargando, setCargando] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setCargando(true);

    try {
      const session = await login(correo, password);

      if (session.rol === "ADMIN_CLINICA") {
        navigate("/pacientes");
      } else {
        navigate("/");
      }
    } catch (err) {
      setError("Correo o contraseña incorrectos.");
    } finally {
      setCargando(false);
    }
  };

  return (
    <main className="auth-page">
      <section className="auth-card">
        <div className="auth-header">
          <h1>Iniciar sesión</h1>
          <p>Accede a la plataforma RedNorte según tu rol.</p>
        </div>

        {error && <div className="alert-error">{error}</div>}

        <form onSubmit={handleSubmit} className="auth-form">
          <label>
            Correo electrónico
            <input
              type="email"
              value={correo}
              onChange={(e) => setCorreo(e.target.value)}
              placeholder="admin@rednorte.cl"
              required
            />
          </label>

          <label>
            Contraseña
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Ingresa tu contraseña"
              required
            />
          </label>

          <button type="submit" className="btn-primary" disabled={cargando}>
            {cargando ? "Validando..." : "Entrar"}
          </button>
        </form>

        <p className="auth-footer">
          ¿Eres paciente nuevo? <Link to="/registro">Crear cuenta</Link>
        </p>
      </section>
    </main>
  );
};

export default LoginPage;