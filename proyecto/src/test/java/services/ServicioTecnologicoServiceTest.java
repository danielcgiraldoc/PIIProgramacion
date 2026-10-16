package services;

import org.example.model.composite.ElementoContratado;
import org.example.model.composite.ServicioTecnologico;
import org.example.services.ServicioTecnologicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTecnologicoServiceTest {

    private static final double DELTA = 0.001;

    private ServicioTecnologicoService servicioService;

    @BeforeEach
    void setUp() {
        EmpresaTestUtil.reiniciar();
        servicioService = new ServicioTecnologicoService();
    }

    @Test
    void crearServiciosDeTodosLosTipos() {
        ServicioTecnologico preventivo = servicioService.crearServicio("PREVENTIVO", "Preventivo", "SRV-001", "Descripcion");
        ServicioTecnologico correctivo = servicioService.crearServicio("CORRECTIVO", "Correctivo", "SRV-002", "Descripcion");
        ServicioTecnologico redes = servicioService.crearServicio("REDES", "Redes", "SRV-003", "Descripcion");
        ServicioTecnologico recuperacion = servicioService.crearServicio("RECUPERACION", "Recuperacion", "SRV-004", "Descripcion");

        assertEquals(80000, preventivo.calcularCosto(), DELTA);
        assertEquals(120000, correctivo.calcularCosto(), DELTA);
        assertEquals(180000, redes.calcularCosto(), DELTA);
        assertEquals(150000, recuperacion.calcularCosto(), DELTA);
        assertEquals(4, servicioService.listarServicios().size());
    }

    @Test
    void crearServicioConTipoInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> servicioService.crearServicio("OTRO", "Servicio", "SRV-001", "Descripcion"));
    }

    @Test
    void crearServicioConCodigoRepetido() {
        servicioService.crearServicio("REDES", "Redes", "SRV-001", "Descripcion");

        assertThrows(IllegalArgumentException.class,
                () -> servicioService.crearServicio("PREVENTIVO", "Preventivo", "SRV-001", "Descripcion"));
    }

    @Test
    void buscarPorCodigo() {
        ServicioTecnologico servicio = servicioService.crearServicio("REDES", "Redes", "SRV-001", "Descripcion");

        assertEquals(servicio, servicioService.buscarPorCodigo("SRV-001"));
        assertNull(servicioService.buscarPorCodigo("NO-EXISTE"));
    }

    @Test
    void personalizarConUnComplemento() {
        ServicioTecnologico servicio = servicioService.crearServicio("CORRECTIVO", "Correctivo", "SRV-001", "Descripcion");

        ElementoContratado personalizado = servicioService.personalizarServicio(servicio, true, false, false);

        assertEquals(155000, personalizado.calcularCosto(), DELTA);
    }

    @Test
    void personalizarConVariosComplementos() {
        ServicioTecnologico servicio = servicioService.crearServicio("CORRECTIVO", "Correctivo", "SRV-001", "Descripcion");

        ElementoContratado personalizado = servicioService.personalizarServicio(servicio, true, true, true);

        assertEquals(230000, personalizado.calcularCosto(), DELTA);
    }

    @Test
    void personalizarNoModificaElCatalogo() {
        ServicioTecnologico servicio = servicioService.crearServicio("CORRECTIVO", "Correctivo", "SRV-001", "Descripcion");

        servicioService.personalizarServicio(servicio, true, true, true);

        assertEquals(120000, servicio.calcularCosto(), DELTA);
        assertEquals(120000, servicio.getCostoBase(), DELTA);
    }
}