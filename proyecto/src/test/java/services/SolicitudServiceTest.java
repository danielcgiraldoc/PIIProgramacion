package services;

import org.example.model.Cliente;
import org.example.model.Solicitud;
import org.example.model.Tecnico;
import org.example.model.composite.ElementoContratado;
import org.example.model.composite.PaqueteServicio;
import org.example.model.composite.ServicioTecnologico;
import org.example.services.ClienteService;
import org.example.services.PaqueteService;
import org.example.services.ServicioTecnologicoService;
import org.example.services.SolicitudService;
import org.example.services.TecnicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudServiceTest {
    private static final double DELTA = 0.001;

    private ClienteService clienteService;
    private ServicioTecnologicoService servicioService;
    private PaqueteService paqueteService;
    private SolicitudService solicitudService;
    private Cliente cliente;
    private ServicioTecnologico preventivo;
    private ServicioTecnologico correctivo;
    private ServicioTecnologico redes;

    @BeforeEach
    void setUp() {
        /**
         * Metodo que inicializa la empresa
         */
        EmpresaTestUtil.reiniciar();
        clienteService = new ClienteService();
        servicioService = new ServicioTecnologicoService();
        paqueteService = new PaqueteService();
        TecnicoService tecnicoService = new TecnicoService(especialidad -> new Tecnico("TEC-105", true, 30000));
        solicitudService = new SolicitudService(clienteService, tecnicoService);

        cliente = clienteService.registrarCliente("1094", "Daniel Giraldo", "daniel@correo.com");
        preventivo = servicioService.crearServicio("PREVENTIVO", "Preventivo", "SRV-001", "Descripcion");
        correctivo = servicioService.crearServicio("CORRECTIVO", "Correctivo", "SRV-002", "Descripcion");
        redes = servicioService.crearServicio("REDES", "Redes", "SRV-003", "Descripcion");
    }

    /**
     * Metodo para armar la solicitud a probar
     * @param codigo
     * @param clienteSolicitud
     * @param presencial
     * @param tecnico
     * @param elementos
     * @return
     */
    private Solicitud armarSolicitud(String codigo, Cliente clienteSolicitud, boolean presencial,
                                     Tecnico tecnico, ElementoContratado... elementos) {
        Solicitud solicitud = new Solicitud(codigo, LocalDate.now(), presencial, 0);
        solicitud.setTheCliente(clienteSolicitud);
        solicitud.setTheTecnico(tecnico);
        for (ElementoContratado elemento : elementos) {
            solicitud.getListElementos().add(elemento);
        }
        return solicitud;
    }

    /**
     * Metodo que prueba que a una solicitud se le puedan añadir servicios
     */
    @Test
    void registrarSolicitudConServicios() {
        Solicitud solicitud = armarSolicitud("SOL-1", cliente, false, null, preventivo, correctivo);

        Solicitud registrada = solicitudService.registrarSolicitud(solicitud);

        assertEquals("SOL-1", registrada.getCodigo());
        assertEquals("1094", registrada.getTheCliente().getDocumento());
        assertEquals(200000, registrada.getCostoTotal(), DELTA);
        assertEquals(1, solicitudService.listarSolicitudes().size());
    }

    /**
     * Metodo que prueba que una solicitud se pueda resgistrar con un paquete y complemento
     */
    @Test
    void registrarSolicitudConServicioPersonalizadoYPaquete() {
        ElementoContratado personalizado = servicioService.personalizarServicio(correctivo, true, true, false);
        PaqueteServicio paquete = paqueteService.crearPaquete("PAQ-001");
        paqueteService.agregarElemento(paquete, preventivo);
        paqueteService.agregarElemento(paquete, redes);

        Solicitud solicitud = armarSolicitud("SOL-1", cliente, false, null, personalizado, paquete);
        solicitudService.registrarSolicitud(solicitud);

        assertEquals(465000, solicitud.getCostoTotal(), DELTA);
    }

    /**
     * Metodo que verifica que la solicitud incluya el costo de desplazamiento del tecnico
     */
    @Test
    void registrarSolicitudPresencialIncluyeDesplazamiento() {
        Tecnico tecnico = new Tecnico("TEC-105", true, 30000);
        Solicitud solicitud = armarSolicitud("SOL-1", cliente, true, tecnico, preventivo);

        solicitudService.registrarSolicitud(solicitud);

        assertEquals(110000, solicitud.getCostoTotal(), DELTA);
    }

    /**
     * Metodo que prueba el registro de una solicitud sin un cliente resgistrado
     */
    @Test
    void registrarSolicitudConClienteNoRegistrado() {
        Cliente noRegistrado = new Cliente.Builder("9999", "Otro Cliente", "otro@correo.com").build();
        Solicitud solicitud = armarSolicitud("SOL-1", noRegistrado, false, null, preventivo);

        assertThrows(IllegalArgumentException.class, () -> solicitudService.registrarSolicitud(solicitud));
        assertEquals(0, solicitudService.listarSolicitudes().size());
    }

    /**
     * Metodo que resgistra una solicitud con condigo repetido
     */
    @Test
    void registrarSolicitudConCodigoRepetido() {
        solicitudService.registrarSolicitud(armarSolicitud("SOL-1", cliente, false, null, preventivo));
        Solicitud repetida = armarSolicitud("SOL-1", cliente, false, null, correctivo);

        assertThrows(IllegalArgumentException.class, () -> solicitudService.registrarSolicitud(repetida));
        assertEquals(1, solicitudService.listarSolicitudes().size());
    }

    /**
     * Metodo que consulta una solicitud por codigo
     *
     */
    @Test
    void consultarSolicitudPorCodigo() {
        Solicitud solicitud = solicitudService.registrarSolicitud(
                armarSolicitud("SOL-1", cliente, false, null, preventivo));

        assertEquals(solicitud, solicitudService.consultarSolicitud("SOL-1"));
        assertNull(solicitudService.consultarSolicitud("SOL-99"));
    }

    /**
     * Metodo que prueba el metodo para listar las soliicitudes de la empresa
     */
    @Test
    void listarSolicitudes() {
        solicitudService.registrarSolicitud(armarSolicitud("SOL-1", cliente, false, null, preventivo));
        solicitudService.registrarSolicitud(armarSolicitud("SOL-2", cliente, false, null, correctivo));

        assertEquals(2, solicitudService.listarSolicitudes().size());
    }

    /**
     * Metodo que prueba el metodo para calcular un costo de solicitud cuando no se requiere atencion presencial
     */
    @Test
    void calcularCostoTotalSinAtencionPresencial() {
        Solicitud solicitud = armarSolicitud("SOL-1", cliente, false, null, preventivo, correctivo);

        assertEquals(200000, solicitudService.calcularCostoTotal(solicitud), DELTA);
    }

    /**
     * Metodo que prueba el metodo para calcular un costo de solicitud cuando se requiere atencion presencial
     */
    @Test
    void calcularCostoTotalConAtencionPresencial() {
        Tecnico tecnico = new Tecnico("TEC-105", true, 30000);
        Solicitud solicitud = armarSolicitud("SOL-1", cliente, true, tecnico, preventivo, correctivo);

        assertEquals(230000, solicitudService.calcularCostoTotal(solicitud), DELTA);
    }

    /**
     * Metodo que prueba que no se permitan agregar cargos duplicados al costo final
     */
    @Test
    void calcularCostoTotalVariasVecesNoDuplicaCargos() {
        Tecnico tecnico = new Tecnico("TEC-105", true, 30000);
        Solicitud solicitud = armarSolicitud("SOL-1", cliente, true, tecnico, preventivo);

        double primero = solicitudService.calcularCostoTotal(solicitud);
        double segundo = solicitudService.calcularCostoTotal(solicitud);

        assertEquals(110000, primero, DELTA);
        assertEquals(primero, segundo, DELTA);
    }

}