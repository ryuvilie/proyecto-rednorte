import { bffApiClient } from "../../../api/apiClient";

export const obtenerHorasDisponibles = async () => {
  return await bffApiClient("/citas/disponibles");
};

export const reservarHora = async (citaId, pacienteId) => {
  return await bffApiClient(
    `/citas/${citaId}/reservar-paciente/${pacienteId}`,
    {
      method: "PUT",
    }
  );
};

export const eliminarCita = (id) => {
  return bffApiClient(`/citas/${id}`, {
    method: "DELETE",
  });
};