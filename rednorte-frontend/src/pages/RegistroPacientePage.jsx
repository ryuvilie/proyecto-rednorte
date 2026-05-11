import { useState } from "react";
import { crearPaciente } from "../services/pacienteService";

const RegistroPacientePage = () => {
  const [paciente, setPaciente] = useState({
    nombre: "",
    apellido: "",
    rut: "",
    fechaNacimiento: "",
    telefono: "",
    correo: "",
  });

  const handleChange = (e) => {
    setPaciente({
      ...paciente,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

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
            required
          />

          <input
            type="text"
            name="nombre"
            placeholder="Nombre"
            value={paciente.nombre}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="apellido"
            placeholder="Apellido"
            value={paciente.apellido}
            onChange={handleChange}
            required
          />

          <input
            type="date"
            name="fechaNacimiento"
            value={paciente.fechaNacimiento}
            onChange={handleChange}
          />

          <input
            type="text"
            name="telefono"
            placeholder="Teléfono"
            value={paciente.telefono}
            onChange={handleChange}
          />

          <input
            type="email"
            name="correo"
            placeholder="Correo"
            value={paciente.correo}
            onChange={handleChange}
          />

          <button type="submit" className="btn-primary">
            Registrar paciente
          </button>
        </form>
      </div>
    </main>
  );
};

export default RegistroPacientePage;