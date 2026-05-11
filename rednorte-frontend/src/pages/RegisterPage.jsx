import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { register } from "../services/authService";

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
  const [cargando, setCargando] = useState(false);

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });

    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
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
                required
              />
            </label>

            <label>
              Nombre
              <input
                name="nombre"
                value={form.nombre}
                onChange={handleChange}
                required
              />
            </label>

            <label>
              Apellido
              <input
                name="apellido"
                value={form.apellido}
                onChange={handleChange}
                required
              />
            </label>

            <label>
              Fecha de nacimiento
              <input
                type="date"
                name="fechaNacimiento"
                value={form.fechaNacimiento}
                onChange={handleChange}
              />
            </label>

            <label>
              Teléfono
              <input
                name="telefono"
                value={form.telefono}
                onChange={handleChange}
              />
            </label>

            <label>
              Correo
              <input
                type="email"
                name="correo"
                value={form.correo}
                onChange={handleChange}
                required
              />
            </label>
          </div>

          <label>
            Contraseña
            <input
              type="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              required
            />
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