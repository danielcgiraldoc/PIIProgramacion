package org.example.controller;

import org.example.model.composite.ElementoContratado;
import org.example.model.composite.ServicioTecnologico;
import org.example.services.ServicioTecnologicoService;

import java.util.List;

public class ServicioController {

    private final ServicioTecnologicoService servicioService = new ServicioTecnologicoService();

    public ServicioTecnologico crearServicio(String tipo, String nombre, String codigo, String descripcion) {
        return servicioService.crearServicio(tipo, nombre, codigo, descripcion);
    }

    public List<ServicioTecnologico> listarServicios() {
        return servicioService.listarServicios();
    }

    public ElementoContratado personalizarServicio(
            ServicioTecnologico servicio,
            boolean prioritaria,
            boolean soporteMensual,
            boolean domicilio) {
        return servicioService.personalizarServicio(servicio, prioritaria, soporteMensual, domicilio);
    }
}