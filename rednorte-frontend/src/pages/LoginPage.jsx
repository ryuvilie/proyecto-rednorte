import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../services/authService";
import { validarEmail, validarPassword } from "../utils/validations";

const LoginPage = () => {
  const navigate = useNavigate();

  const [correo, setCorreo] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [errores, setErrores] = useState({});
  const [cargando, setCargando] = useState(false);

  const validarFormulario = () => {
    const nuevosErrores = {};

    if (!validarEmail(correo)) {
      nuevosErrores.correo = "Ingrese un correo válido.";
    }

    if (!validarPassword(password)) {
      nuevosErrores.password = "La contraseña debe tener al menos 4 caracteres.";
    }

    setErrores(nuevosErrores);

    return Object.keys(nuevosErrores).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!validarFormulario()) {
      return;
    }

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
              onChange={(e) => {
                setCorreo(e.target.value);
                setError("");
                setErrores({ ...errores, correo: "" });
              }}
              placeholder="admin@rednorte.cl"
              className={errores.correo ? "input-error" : ""}
            />
            {errores.correo && (
              <small className="error-text">{errores.correo}</small>
            )}
          </label>

          <label>
            Contraseña
            <input
              type="password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
                setError("");
                setErrores({ ...errores, password: "" });
              }}
              placeholder="Ingresa tu contraseña"
              className={errores.password ? "input-error" : ""}
            />
            {errores.password && (
              <small className="error-text">{errores.password}</small>
            )}
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