package org.example.controller;

import org.example.model.composite.ElementoContratado;
import org.example.model.composite.PaqueteServicio;
import org.example.model.composite.ServicioTecnologico;
import org.example.services.PaqueteService;
import org.example.services.ServicioTecnologicoService;

import java.util.List;

public class PaqueteController {

    private final PaqueteService paqueteService = new PaqueteService();
    private final ServicioTecnologicoService servicioService = new ServicioTecnologicoService();

    public PaqueteServicio crearPaquete(String codigo) {
        return paqueteService.crearPaquete(codigo);
    }

    public List<PaqueteServicio> listarPaquetes() {
        return paqueteService.listarPaquetes();
    }

    public List<ServicioTecnologico> listarServicios() {
        return servicioService.listarServicios();
    }

    public void agregarElemento(PaqueteServicio paquete, ElementoContratado elemento) {
        paqueteService.agregarElemento(paquete, elemento);
    }

    public void eliminarElemento(PaqueteServicio paquete, ElementoContratado elemento) {
        paqueteService.eliminarElemento(paquete, elemento);
    }

    public List<ElementoContratado> consultarElementos(PaqueteServicio paquete) {
        return paqueteService.consultarElementos(paquete);
    }

    public double calcularCostoTotal(PaqueteServicio paquete) {
        return paqueteService.calcularCostoTotal(paquete);
    }
}