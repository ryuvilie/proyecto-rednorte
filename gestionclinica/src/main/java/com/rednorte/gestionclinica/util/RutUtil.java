package com.rednorte.gestionclinica.util;

public class RutUtil {

    public static boolean validarRut(String rut) {

        if (rut == null || rut.isBlank()) {
            return false;
        }

        rut = rut.replace(".", "").replace("-", "");

        if (rut.length() < 2) {
            return false;
        }

        String cuerpo = rut.substring(0, rut.length() - 1);
        char dv = Character.toUpperCase(rut.charAt(rut.length() - 1));

        int suma = 0;
        int multiplo = 2;

        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * multiplo;
            multiplo = (multiplo < 7) ? multiplo + 1 : 2;
        }

        int resto = 11 - (suma % 11);

        char dvEsperado;

        if (resto == 11) {
            dvEsperado = '0';
        } else if (resto == 10) {
            dvEsperado = 'K';
        } else {
            dvEsperado = (char) (resto + '0');
        }

        return dv == dvEsperado;
    }
}