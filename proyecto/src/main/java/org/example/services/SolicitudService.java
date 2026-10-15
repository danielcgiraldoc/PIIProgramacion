package org.example.services;

import org.example.model.Empresa;
import org.example.model.Solicitud;
import org.example.model.Tecnico;
import org.example.model.composite.ElementoContratado;

import java.util.List;

public class SolicitudService {

    private final Empresa empresa = Empresa.getInstance();
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    /**
     * Metodo que permite asignar un cliente y un tecnico a la solicitud
     * @param clienteService
     * @param tecnicoService
     */
    public SolicitudService(ClienteService clienteService, TecnicoService tecnicoService) {
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    /**
     * Metodo para generar el codigo de la solicitud con base en la posicion que ocupa en la lista
     * @return
     */
    public String generarCodigo() {
        return "SOL-" + (empresa.getListSolicitudes().size() + 1);
    }

    public SolicitudServicio registrarSolicitud(SolicitudServicio solicitud) {
        if (clienteService.buscarPorDocumento(solicitud.getCliente().getDocumento()) == null) {
            throw new IllegalArgumentException("El cliente no está registrado");
        }
        if (consultarSolicitud(solicitud.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una solicitud con ese código");
        }
        solicitud.setCostoTotal(calcularCostoTotal(solicitud));
        empresa.getListSolicitudes().add(solicitud);
        return solicitud;
    }

    public SolicitudServicio consultarSolicitud(String codigo) {
        for (SolicitudServicio solicitud : empresa.getListSolicitudes()) {
            if (solicitud.getCodigo().equals(codigo)) {
                return solicitud;
            }
        }
        return null;
    }

    public List<SolicitudServicio> listarSolicitudes() {
        return empresa.getListSolicitudes();
    }

    public double calcularCostoTotal(SolicitudServicio solicitud) {
        double total = 0;
        for (ElementoContratado elemento : solicitud.getElementos()) {
            total += elemento.calcularCosto();
        }
        if (solicitud.isPresencial()) {
            total += solicitud.getCostoDesplazamiento();
        }
        return total;
    }

    public void confirmarSolicitud(String codigo) {
        SolicitudServicio solicitud = consultarSolicitud(codigo);
        if (solicitud == null) {
            throw new IllegalArgumentException("No existe la solicitud " + codigo);
        }
        if (solicitud.isPresencial()) {
            TecnicoDisponibilidad tecnico = tecnicoService.consultarDisponibilidad(solicitud.getEspecialidad());
            if (!tecnico.isDisponible()) {
                throw new IllegalStateException("No hay técnicos disponibles, la solicitud no puede confirmarse");
            }
            solicitud.setIdTecnico(tecnico.getIdTecnico());
            solicitud.setCostoDesplazamiento(tecnico.getCostoDesplazamiento());
        }
        solicitud.setCostoTotal(calcularCostoTotal(solicitud));
        solicitud.setConfirmada(true);
    }
}