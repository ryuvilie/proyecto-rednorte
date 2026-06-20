import { bffApiClient } from "../../../api/apiClient";

export const obtenerReportes = () => {
  return bffApiClient("/reportes");
};

export const obtenerNotificaciones = () => {
  return bffApiClient("/notificaciones");
};

export const obtenerPacientesReporte = () => {
  return bffApiClient("/pacientes");
};

export const obtenerListaEsperaReporte = () => {
  return bffApiClient("/lista-espera");
};

export const obtenerCitasReporte = () => {
  return bffApiClient("/citas");
};