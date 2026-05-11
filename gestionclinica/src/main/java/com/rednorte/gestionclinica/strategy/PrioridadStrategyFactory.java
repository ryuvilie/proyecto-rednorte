package com.rednorte.gestionclinica.strategy;

import org.springframework.stereotype.Component;

@Component
public class PrioridadStrategyFactory {

    private final PrioridadAltaStrategy prioridadAltaStrategy;
    private final PrioridadMediaStrategy prioridadMediaStrategy;
    private final PrioridadBajaStrategy prioridadBajaStrategy;

    public PrioridadStrategyFactory(
            PrioridadAltaStrategy prioridadAltaStrategy,
            PrioridadMediaStrategy prioridadMediaStrategy,
            PrioridadBajaStrategy prioridadBajaStrategy
    ) {
        this.prioridadAltaStrategy = prioridadAltaStrategy;
        this.prioridadMediaStrategy = prioridadMediaStrategy;
        this.prioridadBajaStrategy = prioridadBajaStrategy;
    }

    public PrioridadStrategy obtenerEstrategia(String prioridad) {

        if (prioridad == null || prioridad.isBlank()) {
            throw new RuntimeException("La prioridad es obligatoria");
        }

        return switch (prioridad.toLowerCase()) {
            case "alta" -> prioridadAltaStrategy;
            case "media" -> prioridadMediaStrategy;
            case "baja" -> prioridadBajaStrategy;

            default -> throw new RuntimeException(
                    "Prioridad inválida. Debe ser ALTA, MEDIA o BAJA"
            );
        };
}
}