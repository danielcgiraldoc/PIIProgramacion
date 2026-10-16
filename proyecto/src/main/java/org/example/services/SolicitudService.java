package org.example.services;

import org.example.model.Empresa;
import org.example.model.Solicitud;
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

    /**
     * Metodo para registrar una solicitud
     * @param solicitud
     * @return
     */
    public Solicitud registrarSolicitud(Solicitud solicitud) {
        if (clienteService.buscarPorDocumento(solicitud.getTheCliente().getDocumento()) == null) {
            throw new IllegalArgumentException("El cliente no está registrado");
        }
        if (consultarSolicitud(solicitud.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una solicitud con ese código");
        }
        solicitud.setCostoTotal(calcularCostoTotal(solicitud));
        empresa.getListSolicitudes().add(solicitud);
        return solicitud;
    }

    /**
     * Metodo para consultar una solicitud por su codigo
     * @param codigo
     * @return
     */
    public Solicitud consultarSolicitud(String codigo) {
        for (Solicitud solicitud : empresa.getListSolicitudes()) {
            if (solicitud.getCodigo().equals(codigo)) {
                return solicitud;
            }
        }
        return null;
    }

    /**
     * Metodo que permite listar las solicitudes de la empresa
     * @return
     */
    public List<Solicitud> listarSolicitudes() {
        return empresa.getListSolicitudes();
    }

    /**
     * Metodo que permite calcular el costo total de una solicitud
     * @param solicitud
     * @return
     */
    public double calcularCostoTotal(Solicitud solicitud) {
        double total = 0;
        for (ElementoContratado elemento : solicitud.getListElementos()) {
            total += elemento.calcularCosto();
        }
        if (solicitud.isAtencionPresencial()) {
            total += solicitud.getTheTecnico().getCostoDesplazamiento();
        }
        return total;
    }


}