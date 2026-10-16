package org.example.controller;

import org.example.model.Cliente;
import org.example.model.Empresa;
import org.example.model.Solicitud;
import org.example.model.Tecnico;
import org.example.model.adapter.LegacySupportAdapter;
import org.example.model.adapter.ProveedorTecnicos;
import org.example.model.composite.ElementoContratado;
import org.example.model.composite.PaqueteServicio;
import org.example.model.composite.ServicioTecnologico;
import org.example.model.external.LegacySupport;
import org.example.services.ClienteService;
import org.example.services.PaqueteService;
import org.example.services.ServicioTecnologicoService;
import org.example.services.SolicitudService;
import org.example.services.TecnicoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudController {

    private final Empresa empresa = Empresa.getInstance();
    private final ClienteService clienteService = new ClienteService();
    private final PaqueteService paqueteService = new PaqueteService();
    private final ServicioTecnologicoService servicioService =
            new ServicioTecnologicoService();
    private final TecnicoService tecnicoService;
    private final SolicitudService solicitudService;

    public SolicitudController() {
        if (empresa.getListSolicitudes() == null) {
            empresa.setListSolicitudes(new ArrayList<>());
        }

        ProveedorTecnicos proveedor = new LegacySupportAdapter(new LegacySupport());

        tecnicoService = new TecnicoService(proveedor);
        solicitudService = new SolicitudService(clienteService, tecnicoService);
    }

    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    public List<ElementoContratado> listarElementosDisponibles() {
        List<ElementoContratado> elementos = new ArrayList<>();
        elementos.addAll(servicioService.listarServicios());
        elementos.addAll(paqueteService.listarPaquetes());
        return elementos;
    }

    public Tecnico consultarTecnico(String especialidad) {
        return tecnicoService.consultarDisponibilidad(especialidad);
    }

    public Solicitud registrarSolicitud(Cliente cliente, List<ElementoContratado> elementos, boolean atencionPresencial, Tecnico tecnico) {

        if (cliente == null) {
            throw new IllegalArgumentException("Selecciona un cliente.");
        }

        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException("Agrega al menos un servicio o paquete.");
        }

        if (atencionPresencial && (tecnico == null || !tecnico.isDisponible())) {
            throw new IllegalArgumentException("No se puede confirmar la solicitud sin un técnico disponible.");
        }

        Solicitud solicitud = new Solicitud(solicitudService.generarCodigo(), LocalDate.now(), atencionPresencial, 0);

        solicitud.setTheCliente(cliente);
        solicitud.setListElementos(new ArrayList<>(elementos));

        if (atencionPresencial) {
            solicitud.setTheTecnico(tecnico);
        }

        return solicitudService.registrarSolicitud(solicitud);
    }

    public List<Solicitud> listarSolicitudes() {
        return solicitudService.listarSolicitudes();
    }
}