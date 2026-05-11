import { bffApiClient } from "../api/apiClient";

export const obtenerReportes = () => {
  return bffApiClient("/reportes");
};

export const obtenerNotificaciones = () => {
  return bffApiClient("/notificaciones");
};