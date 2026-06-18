package com.rednorte.gestionclinica.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrioridadStrategyFactoryTest {

    private PrioridadAltaStrategy altaStrategy;
    private PrioridadMediaStrategy mediaStrategy;
    private PrioridadBajaStrategy bajaStrategy;
    private PrioridadStrategyFactory factory;

    @BeforeEach
    void setUp() {
        altaStrategy = new PrioridadAltaStrategy();
        mediaStrategy = new PrioridadMediaStrategy();
        bajaStrategy = new PrioridadBajaStrategy();

        factory = new PrioridadStrategyFactory(
                altaStrategy,
                mediaStrategy,
                bajaStrategy
        );
    }

    @Test
    void debeRetornarStrategyAlta() {
        PrioridadStrategy strategy = factory.obtenerEstrategia("alta");

        assertSame(altaStrategy, strategy);
    }

    @Test
    void debeRetornarStrategyMedia() {
        PrioridadStrategy strategy = factory.obtenerEstrategia("media");

        assertSame(mediaStrategy, strategy);
    }

    @Test
    void debeRetornarStrategyBaja() {
        PrioridadStrategy strategy = factory.obtenerEstrategia("baja");

        assertSame(bajaStrategy, strategy);
    }

    @Test
    void debeLanzarErrorSiPrioridadEsNula() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            factory.obtenerEstrategia(null);
        });

        assertEquals("La prioridad es obligatoria", exception.getMessage());
    }

    @Test
    void debeLanzarErrorSiPrioridadEsInvalida() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            factory.obtenerEstrategia("urgente");
        });

        assertEquals("Prioridad inválida. Debe ser ALTA, MEDIA o BAJA", exception.getMessage());
    }
}