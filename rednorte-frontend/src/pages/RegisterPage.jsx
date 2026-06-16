import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { register } from "../services/authService";
import {
  validarCampoObligatorio,
  validarEmail,
  validarTelefono,
  validarRut,
  validarFecha,
  validarPassword,
} from "../utils/validations";

const RegisterPage = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    rut: "",
    nombre: "",
    apellido: "",
    fechaNacimiento: "",
    telefono: "",
    correo: "",
    password: "",
  });

  const [error, setError] = useState("");
  const [errores, setErrores] = useState({});
  const [cargando, setCargando] = useState(false);

  const validarFormulario = () => {
    const nuevosErrores = {};

    if (!validarRut(form.rut)) {
      nuevosErrores.rut = "Ingrese un RUT válido.";
    }

    if (!validarCampoObligatorio(form.nombre)) {
      nuevosErrores.nombre = "El nombre es obligatorio.";
    }

    if (!validarCampoObligatorio(form.apellido)) {
      nuevosErrores.apellido = "El apellido es obligatorio.";
    }

    if (!validarFecha(form.fechaNacimiento)) {
      nuevosErrores.fechaNacimiento = "La fecha de nacimiento es obligatoria.";
    }

    if (!validarTelefono(form.telefono)) {
      nuevosErrores.telefono = "Ingrese un teléfono válido de al menos 8 números.";
    }

    if (!validarEmail(form.correo)) {
      nuevosErrores.correo = "Ingrese un correo válido.";
    }

    if (!validarPassword(form.password)) {
      nuevosErrores.password = "La contraseña debe tener al menos 5 caracteres.";
    }

    setErrores(nuevosErrores);

    return Object.keys(nuevosErrores).length === 0;
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });

    setError("");

    setErrores({
      ...errores,
      [e.target.name]: "",
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validarFormulario()) {
      return;
    }

    setCargando(true);

    try {
      await register(form);
      navigate("/");
    } catch (err) {
      setError("No se pudo crear la cuenta. Verifica los datos ingresados.");
    } finally {
      setCargando(false);
    }
  };

  return (
    <main className="auth-page">
      <section className="auth-card auth-card-large">
        <div className="auth-header">
          <h1>Registro de paciente</h1>
          <p>Crea tu cuenta para consultar tu información clínica.</p>
        </div>

        {error && <div className="alert-error">{error}</div>}

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="form-grid">
            <label>
              RUT
              <input
                name="rut"
                value={form.rut}
                onChange={handleChange}
                placeholder="12345678-9"
                className={errores.rut ? "input-error" : ""}
              />
              {errores.rut && <small className="error-text">{errores.rut}</small>}
            </label>

            <label>
              Nombre
              <input
                name="nombre"
                value={form.nombre}
                onChange={handleChange}
                className={errores.nombre ? "input-error" : ""}
              />
              {errores.nombre && (
                <small className="error-text">{errores.nombre}</small>
              )}
            </label>

            <label>
              Apellido
              <input
                name="apellido"
                value={form.apellido}
                onChange={handleChange}
                className={errores.apellido ? "input-error" : ""}
              />
              {errores.apellido && (
                <small className="error-text">{errores.apellido}</small>
              )}
            </label>

            <label>
              Fecha de nacimiento
              <input
                type="date"
                name="fechaNacimiento"
                value={form.fechaNacimiento}
                onChange={handleChange}
                className={errores.fechaNacimiento ? "input-error" : ""}
              />
              {errores.fechaNacimiento && (
                <small className="error-text">{errores.fechaNacimiento}</small>
              )}
            </label>

            <label>
              Teléfono
              <input
                name="telefono"
                value={form.telefono}
                onChange={handleChange}
                className={errores.telefono ? "input-error" : ""}
              />
              {errores.telefono && (
                <small className="error-text">{errores.telefono}</small>
              )}
            </label>

            <label>
              Correo
              <input
                type="email"
                name="correo"
                value={form.correo}
                onChange={handleChange}
                className={errores.correo ? "input-error" : ""}
              />
              {errores.correo && (
                <small className="error-text">{errores.correo}</small>
              )}
            </label>
          </div>

          <label>
            Contraseña
            <input
              type="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              className={errores.password ? "input-error" : ""}
            />
            {errores.password && (
              <small className="error-text">{errores.password}</small>
            )}
          </label>

          <button type="submit" className="btn-primary" disabled={cargando}>
            {cargando ? "Creando cuenta..." : "Crear cuenta"}
          </button>
        </form>

        <p className="auth-footer">
          ¿Ya tienes cuenta? <Link to="/login">Iniciar sesión</Link>
        </p>
      </section>
    </main>
  );
};

export default RegisterPage;