import { bffApiClient } from "../api/apiClient";

const AUTH_STORAGE_KEY = "rednorte_auth";

export const login = async (correo, password) => {
  const response = await bffApiClient("/auth/login", {
    method: "POST",
    body: JSON.stringify({ correo, password }),
  });

  guardarSesion(response);

  return response;
};

export const register = async (datosRegistro) => {
  const response = await bffApiClient("/auth/register", {
    method: "POST",
    body: JSON.stringify(datosRegistro),
  });

  guardarSesion(response);

  return response;
};

export const guardarSesion = (authData) => {
  const expiresAt = Date.now() + authData.expiracionMs;

  const session = {
    ...authData,
    expiresAt,
  };

  localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(session));
};

export const obtenerSesion = () => {
  const sessionText = localStorage.getItem(AUTH_STORAGE_KEY);

  if (!sessionText) {
    return null;
  }

  const session = JSON.parse(sessionText);

  if (Date.now() > session.expiresAt) {
    cerrarSesion();
    return null;
  }

  return session;
};

export const cerrarSesion = () => {
  localStorage.removeItem(AUTH_STORAGE_KEY);
};

export const estaAutenticado = () => {
  return obtenerSesion() !== null;
};

export const obtenerRol = () => {
  const session = obtenerSesion();
  return session ? session.rol : null;
};

export const esAdmin = () => {
  const session = obtenerSesion();
  return session?.rol === "ADMIN_CLINICA";
};

export const esPaciente = () => {
  const session = obtenerSesion();
  return session?.rol === "PACIENTE";
};

export const esDoctor = () => {
  const session = obtenerSesion();
  return session?.rol === "DOCTOR";
};