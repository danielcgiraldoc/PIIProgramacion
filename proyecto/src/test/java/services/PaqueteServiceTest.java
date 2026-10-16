package services;

import org.example.model.composite.ElementoContratado;
import org.example.model.composite.PaqueteServicio;
import org.example.model.composite.ServicioTecnologico;
import org.example.services.PaqueteService;
import org.example.services.ServicioTecnologicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaqueteServiceTest {

    private static final double DELTA = 0.001;

    private PaqueteService paqueteService;
    private ServicioTecnologicoService servicioService;
    private ServicioTecnologico preventivo;
    private ServicioTecnologico correctivo;
    private ServicioTecnologico redes;

    @BeforeEach
    void setUp() {
        EmpresaTestUtil.reiniciar();
        paqueteService = new PaqueteService();
        servicioService = new ServicioTecnologicoService();
        preventivo = servicioService.crearServicio("PREVENTIVO", "Preventivo", "SRV-001", "Descripcion");
        correctivo = servicioService.crearServicio("CORRECTIVO", "Correctivo", "SRV-002", "Descripcion");
        redes = servicioService.crearServicio("REDES", "Redes", "SRV-003", "Descripcion");
    }

    @Test
    void crearPaquete() {
        PaqueteServicio paquete = paqueteService.crearPaquete("PAQ-001");

        assertNotNull(paquete);
        assertEquals(1, paqueteService.listarPaquetes().size());
    }

    @Test
    void crearPaqueteConCodigoRepetido() {
        paqueteService.crearPaquete("PAQ-001");

        assertThrows(IllegalArgumentException.class, () -> paqueteService.crearPaquete("PAQ-001"));
    }

    @Test
    void agregarServiciosYCalcularCosto() {
        PaqueteServicio paquete = paqueteService.crearPaquete("PAQ-001");

        paqueteService.agregarElemento(paquete, preventivo);
        paqueteService.agregarElemento(paquete, correctivo);

        assertEquals(2, paqueteService.consultarElementos(paquete).size());
        assertEquals(200000, paqueteService.calcularCostoTotal(paquete), DELTA);
    }

    @Test
    void agregarServicioPersonalizado() {
        PaqueteServicio paquete = paqueteService.crearPaquete("PAQ-001");
        ElementoContratado personalizado = servicioService.personalizarServicio(correctivo, true, false, false);

        paqueteService.agregarElemento(paquete, personalizado);

        assertEquals(155000, paqueteService.calcularCostoTotal(paquete), DELTA);
    }

    @Test
    void paqueteDentroDeOtroPaquete() {
        PaqueteServicio mantenimiento = paqueteService.crearPaquete("PAQ-001");
        paqueteService.agregarElemento(mantenimiento, preventivo);
        paqueteService.agregarElemento(mantenimiento, correctivo);

        PaqueteServicio empresarial = paqueteService.crearPaquete("PAQ-002");
        paqueteService.agregarElemento(empresarial, mantenimiento);
        paqueteService.agregarElemento(empresarial, redes);

        assertEquals(380000, paqueteService.calcularCostoTotal(empresarial), DELTA);
    }

    @Test
    void paqueteNoPuedeIncluirseASiMismo() {
        PaqueteServicio paquete = paqueteService.crearPaquete("PAQ-001");

        assertThrows(IllegalArgumentException.class, () -> paqueteService.agregarElemento(paquete, paquete));
    }

    @Test
    void paqueteNoPuedeIncluirseIndirectamente() {
        PaqueteServicio a = paqueteService.crearPaquete("A");
        PaqueteServicio b = paqueteService.crearPaquete("B");
        paqueteService.agregarElemento(a, b);

        assertThrows(IllegalArgumentException.class, () -> paqueteService.agregarElemento(b, a));
    }
}