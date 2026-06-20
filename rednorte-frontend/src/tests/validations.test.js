import { describe, it, expect } from "vitest";

import {
  validarCampoObligatorio,
  validarEmail,
  validarTelefono,
  validarRut,
  validarFecha,
  validarPassword,
} from "../utils/validations";

describe("Validaciones frontend RedNorte", () => {
  describe("validarCampoObligatorio", () => {
    it("debe retornar true si el campo tiene texto", () => {
      expect(validarCampoObligatorio("Paciente")).toBe(true);
    });

    it("debe retornar false si el campo está vacío", () => {
      expect(validarCampoObligatorio("")).toBe(false);
    });

    it("debe retornar false si el campo solo tiene espacios", () => {
      expect(validarCampoObligatorio("   ")).toBe(false);
    });
  });

  describe("validarEmail", () => {
    it("debe retornar true para un correo válido", () => {
      expect(validarEmail("paciente@rednorte.cl")).toBe(true);
    });

    it("debe retornar false para un correo inválido", () => {
      expect(validarEmail("pacienterednorte.cl")).toBe(false);
    });
  });

  describe("validarTelefono", () => {
    it("debe retornar true si el teléfono tiene al menos 8 dígitos", () => {
      expect(validarTelefono("912345678")).toBe(true);
    });

    it("debe retornar true aunque el teléfono tenga espacios o símbolos", () => {
      expect(validarTelefono("+56 9 1234 5678")).toBe(true);
    });

    it("debe retornar false si el teléfono tiene menos de 8 dígitos", () => {
      expect(validarTelefono("12345")).toBe(false);
    });
  });

  describe("validarRut", () => {
    it("debe retornar true si el RUT tiene al menos 8 caracteres", () => {
      expect(validarRut("12345678-9")).toBe(true);
    });

    it("debe retornar false si el RUT es muy corto", () => {
      expect(validarRut("123")).toBe(false);
    });
  });

  describe("validarFecha", () => {
    it("debe retornar true si existe una fecha", () => {
      expect(validarFecha("2026-06-18")).toBe(true);
    });

    it("debe retornar false si la fecha está vacía", () => {
      expect(validarFecha("")).toBe(false);
    });
  });

  describe("validarPassword", () => {
    it("debe retornar true si la contraseña tiene al menos 4 caracteres", () => {
      expect(validarPassword("1234")).toBe(true);
    });

    it("debe retornar true si la contraseña tiene más de 4 caracteres", () => {
      expect(validarPassword("clave123")).toBe(true);
    });

    it("debe retornar false si la contraseña tiene menos de 4 caracteres", () => {
      expect(validarPassword("12")).toBe(false);
    });

    it("debe retornar false si la contraseña está vacía", () => {
      expect(validarPassword("")).toBe(false);
    });
  });
});