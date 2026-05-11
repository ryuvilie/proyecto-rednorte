import { bffApiClient } from "../api/apiClient";

export const obtenerPacientes = () => {
  return bffApiClient("/pacientes");
};

export const crearPaciente = (paciente) => {
  return bffApiClient("/pacientes", {
    method: "POST",
    body: JSON.stringify(paciente),
  });
};

export const eliminarPaciente = (id) => {
  return bffApiClient(`/pacientes/${id}`, {
    method: "DELETE",
  });
};

export const limpiarDependenciasPaciente = (id) => {
  return bffApiClient(`/pacientes/${id}/dependencias`, {
    method: "DELETE",
  });
};