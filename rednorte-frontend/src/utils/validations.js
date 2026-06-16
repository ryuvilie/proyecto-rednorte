export const validarCampoObligatorio = (valor) => {
  return valor && valor.trim() !== "";
};

export const validarEmail = (email) => {
  return /\S+@\S+\.\S+/.test(email);
};

export const validarTelefono = (telefono) => {
  return telefono.replace(/\D/g, "").length >= 8;
};

export const validarRut = (rut) => {
  return rut.trim().length >= 8;
};

export const validarFecha = (fecha) => {
  return fecha && fecha !== "";
};

export const validarPassword = (password) => {
  return password && password.trim().length >= 4;
};