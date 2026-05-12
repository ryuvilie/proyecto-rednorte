import { bffApiClient } from "../api/apiClient";

export const obtenerCitas = () => {
  return bffApiClient("/citas");
};

export const crearCita = (cita) => {
  return bffApiClient("/citas", {
    method: "POST",
    body: JSON.stringify(cita),
  });
};

export const obtenerCitasDoctor = (doctorId) => {
  return bffApiClient(`/citas/doctor/${doctorId}`);
};

export const eliminarCita = (id) => {
  return bffApiClient(`/citas/${id}`, {
    method: "DELETE",
  });
};