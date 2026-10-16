package services;

import org.example.model.Tecnico;
import org.example.services.TecnicoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TecnicoServiceTest {

    @Test
    void consultarTecnicoDisponible() {
        TecnicoService tecnicoService = new TecnicoService(especialidad -> new Tecnico("TEC-105", true, 30000));

        Tecnico tecnico = tecnicoService.consultarDisponibilidad("REDES");

        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    @Test
    void consultarSinTecnicoDisponible() {
        TecnicoService tecnicoService = new TecnicoService(especialidad -> new Tecnico("SIN_TECNICO", false, 0));

        Tecnico tecnico = tecnicoService.consultarDisponibilidad("SEGURIDAD");

        assertFalse(tecnico.isDisponible());
    }

    @Test
    void consultarConEspecialidadVacia() {
        TecnicoService tecnicoService = new TecnicoService(especialidad -> new Tecnico("TEC-105", true, 30000));

        assertThrows(IllegalArgumentException.class, () -> tecnicoService.consultarDisponibilidad(""));
    }

    @Test
    void hayTecnicoDisponible() {
        TecnicoService conTecnico = new TecnicoService(especialidad -> new Tecnico("TEC-105", true, 30000));
        TecnicoService sinTecnico = new TecnicoService(especialidad -> new Tecnico("SIN_TECNICO", false, 0));

        assertTrue(conTecnico.hayTecnicoDisponible("REDES"));
        assertFalse(sinTecnico.hayTecnicoDisponible("REDES"));
    }
}