import { bffApiClient } from "../api/apiClient";

export const obtenerListaEspera = () => {
  return bffApiClient("/lista-espera");
};

export const crearListaEspera = (listaEspera) => {
  return bffApiClient("/lista-espera", {
    method: "POST",
    body: JSON.stringify(listaEspera),
  });
};