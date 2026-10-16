package org.example.controller;

import org.example.model.Empresa;
import org.example.model.Solicitud;
import org.example.model.Tecnico;
import org.example.model.adapter.LegacySupportAdapter;
import org.example.model.adapter.ProveedorTecnicos;
import org.example.model.external.LegacySupport;
import org.example.services.ClienteService;
import org.example.services.SolicitudService;
import org.example.services.TecnicoService;

import java.util.ArrayList;
import java.util.List;

public class TecnicoController {

    private final Empresa empresa = Empresa.getInstance();
    private final TecnicoService tecnicoService;
    private final SolicitudService solicitudService;

    public TecnicoController() {
        if (empresa.getListSolicitudes() == null) {
            empresa.setListSolicitudes(new ArrayList<>());
        }

        ProveedorTecnicos proveedor = new LegacySupportAdapter(new LegacySupport());

        tecnicoService = new TecnicoService(proveedor);
        solicitudService = new SolicitudService(new ClienteService(), tecnicoService);
    }

    public Tecnico consultarTecnico(String especialidad) {
        return tecnicoService.consultarDisponibilidad(especialidad);
    }

    public List<Solicitud> listarSolicitudes() {
        return solicitudService.listarSolicitudes();
    }

    public double recalcularCosto(Solicitud solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("Selecciona una solicitud.");
        }

        if (solicitud.isAtencionPresencial() && (solicitud.getTheTecnico() == null || !solicitud.getTheTecnico().isDisponible())) {
            throw new IllegalArgumentException("La solicitud presencial no tiene un técnico disponible.");
        }

        double costo = solicitudService.calcularCostoTotal(solicitud);
        solicitud.setCostoTotal(costo);
        return costo;
    }
}