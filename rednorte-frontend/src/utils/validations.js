export const validarCampoObligatorio = (valor) => {
  return Boolean(valor && valor.trim() !== "");
};

export const validarEmail = (email) => {
  return Boolean(email && /\S+@\S+\.\S+/.test(email));
};

export const validarTelefono = (telefono) => {
  return Boolean(telefono && telefono.replace(/\D/g, "").length >= 8);
};

export const validarRut = (rut) => {
  return Boolean(rut && rut.trim().length >= 8);
};

export const validarFecha = (fecha) => {
  return Boolean(fecha && fecha !== "");
};

export const validarPassword = (password) => {
  return Boolean(password && password.trim().length >= 4);
};