import { useState } from "react";
import { crearPaciente } from "../services/pacienteService";
import {
  validarCampoObligatorio,
  validarEmail,
  validarTelefono,
  validarRut,
  validarFecha,
} from "../utils/validations";

const RegistroPacientePage = () => {
  const [paciente, setPaciente] = useState({
    nombre: "",
    apellido: "",
    rut: "",
    fechaNacimiento: "",
    telefono: "",
    correo: "",
  });

  const [errores, setErrores] = useState({});

  const validarFormulario = () => {
    const nuevosErrores = {};

    if (!validarRut(paciente.rut)) {
      nuevosErrores.rut = "Ingrese un RUT válido.";
    }

    if (!validarCampoObligatorio(paciente.nombre)) {
      nuevosErrores.nombre = "El nombre es obligatorio.";
    }

    if (!validarCampoObligatorio(paciente.apellido)) {
      nuevosErrores.apellido = "El apellido es obligatorio.";
    }

    if (!validarFecha(paciente.fechaNacimiento)) {
      nuevosErrores.fechaNacimiento = "La fecha de nacimiento es obligatoria.";
    }

    if (!validarTelefono(paciente.telefono)) {
      nuevosErrores.telefono = "Ingrese un teléfono válido de al menos 8 números.";
    }

    if (!validarEmail(paciente.correo)) {
      nuevosErrores.correo = "Ingrese un correo válido.";
    }

    setErrores(nuevosErrores);

    return Object.keys(nuevosErrores).length === 0;
  };

  const handleChange = (e) => {
    setPaciente({
      ...paciente,
      [e.target.name]: e.target.value,
    });

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

    try {
      await crearPaciente(paciente);
      alert("Paciente registrado correctamente");

      setPaciente({
        nombre: "",
        apellido: "",
        rut: "",
        fechaNacimiento: "",
        telefono: "",
        correo: "",
      });

      setErrores({});
    } catch (error) {
      console.error("Error al registrar paciente:", error);
      alert("Error al registrar paciente");
    }
  };

  return (
    <main>
      <div className="card">
        <h1>Registro de Paciente</h1>
        <p>Formulario para registrar nuevos pacientes en RedNorte.</p>

        <form onSubmit={handleSubmit} className="form">
          <input
            type="text"
            name="rut"
            placeholder="RUT"
            value={paciente.rut}
            onChange={handleChange}
            className={errores.rut ? "input-error" : ""}
          />
          {errores.rut && <small className="error-text">{errores.rut}</small>}

          <input
            type="text"
            name="nombre"
            placeholder="Nombre"
            value={paciente.nombre}
            onChange={handleChange}
            className={errores.nombre ? "input-error" : ""}
          />
          {errores.nombre && (
            <small className="error-text">{errores.nombre}</small>
          )}

          <input
            type="text"
            name="apellido"
            placeholder="Apellido"
            value={paciente.apellido}
            onChange={handleChange}
            className={errores.apellido ? "input-error" : ""}
          />
          {errores.apellido && (
            <small className="error-text">{errores.apellido}</small>
          )}

          <input
            type="date"
            name="fechaNacimiento"
            value={paciente.fechaNacimiento}
            onChange={handleChange}
            className={errores.fechaNacimiento ? "input-error" : ""}
          />
          {errores.fechaNacimiento && (
            <small className="error-text">{errores.fechaNacimiento}</small>
          )}

          <input
            type="text"
            name="telefono"
            placeholder="Teléfono"
            value={paciente.telefono}
            onChange={handleChange}
            className={errores.telefono ? "input-error" : ""}
          />
          {errores.telefono && (
            <small className="error-text">{errores.telefono}</small>
          )}

          <input
            type="email"
            name="correo"
            placeholder="Correo"
            value={paciente.correo}
            onChange={handleChange}
            className={errores.correo ? "input-error" : ""}
          />
          {errores.correo && (
            <small className="error-text">{errores.correo}</small>
          )}

          <button type="submit" className="btn-primary">
            Registrar paciente
          </button>
        </form>
      </div>
    </main>
  );
};

export default RegistroPacientePage;