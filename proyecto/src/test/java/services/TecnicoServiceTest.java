package services;

import org.example.model.Tecnico;
import org.example.services.TecnicoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TecnicoServiceTest {

    /**
     * Metodo que prueba el metodo para consultar si un tecnico esta disponible
     */
    @Test
    void consultarTecnicoDisponible() {
        TecnicoService tecnicoService = new TecnicoService(especialidad -> new Tecnico("TEC-105", true, 30000));

        Tecnico tecnico = tecnicoService.consultarDisponibilidad("REDES");

        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    /**
     * Metodo que prueba si un tecnico no esta disponible
     */
    @Test
    void consultarSinTecnicoDisponible() {
        TecnicoService tecnicoService = new TecnicoService(especialidad -> new Tecnico("SIN_TECNICO", false, 0));

        Tecnico tecnico = tecnicoService.consultarDisponibilidad("SEGURIDAD");

        assertFalse(tecnico.isDisponible());
    }

    /**
     * Metodo que prueba la consulta de un tecnico cuando la especialidad requerida esta vacia
     */
    @Test
    void consultarConEspecialidadVacia() {
        TecnicoService tecnicoService = new TecnicoService(especialidad -> new Tecnico("TEC-105", true, 30000));

        assertThrows(IllegalArgumentException.class, () -> tecnicoService.consultarDisponibilidad(""));
    }

    /**
     * Metodo que prueba si aun quedan mecanicos de cierta especialidad disponibles
     */
    @Test
    void hayTecnicoDisponible() {
        TecnicoService conTecnico = new TecnicoService(especialidad -> new Tecnico("TEC-105", true, 30000));
        TecnicoService sinTecnico = new TecnicoService(especialidad -> new Tecnico("SIN_TECNICO", false, 0));

        assertTrue(conTecnico.hayTecnicoDisponible("REDES"));
        assertFalse(sinTecnico.hayTecnicoDisponible("REDES"));
    }
}