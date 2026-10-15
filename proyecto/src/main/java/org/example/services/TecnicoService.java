package org.example.services;

import org.example.model.adapter.ProveedorTecnicos;
import org.example.model.Tecnico;

public class TecnicoService {

    private final ProveedorTecnicos proveedor;

    public TecnicoService(ProveedorTecnicos proveedor) {
        this.proveedor = proveedor;
    }

    public Tecnico consultarDisponibilidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad es obligatoria");
        }
        return proveedor.consultarTecnico(especialidad);
    }

    public boolean hayTecnicoDisponible(String especialidad) {
        return consultarDisponibilidad(especialidad).ge();
    }
}