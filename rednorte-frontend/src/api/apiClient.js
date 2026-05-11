const API_BFF_URL = import.meta.env.VITE_API_BFF_URL;

export const bffApiClient = async (endpoint, options = {}) => {
  return request(`${API_BFF_URL}${endpoint}`, options);
};

const request = async (url, options = {}) => {
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    ...options,
  };

  const response = await fetch(url, config);

  if (!response.ok) {
    throw new Error("Error en la petición al BFF");
  }

  // Para DELETE o respuestas sin contenido
  if (response.status === 204) {
    return null;
  }

  const text = await response.text();

  if (!text) {
    return null;
  }

  return JSON.parse(text);
};