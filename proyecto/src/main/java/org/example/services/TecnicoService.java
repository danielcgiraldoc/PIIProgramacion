package org.example.services;

import org.example.model.adapter.ProveedorTecnicos;
import org.example.model.Tecnico;

public class TecnicoService {

    private final ProveedorTecnicos proveedor;

    /**
     * Metodo que constructor de TecnicoService
     * @param proveedor
     */
    public TecnicoService(ProveedorTecnicos proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Metodo que permite consultar un tecnico segun su especialidad
     * @param especialidad
     * @return
     */
    public Tecnico consultarDisponibilidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad es obligatoria");
        }
        return proveedor.consultarTecnico(especialidad);
    }

    /**
     * Metodo que verifica si hay un tecnico disponible segun su especialidad
     * @param especialidad
     * @return
     */
    public boolean hayTecnicoDisponible(String especialidad) {
        return consultarDisponibilidad(especialidad).isDisponible();
    }
}