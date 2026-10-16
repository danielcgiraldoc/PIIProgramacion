package org.example.model.adapter;

import org.example.model.Tecnico;

public interface ProveedorTecnicos {

    /**
     * Metodo que permite consultar ell tecnico por especialidad
     * @param especialidad
     * @return
     */
    Tecnico consultarTecnico(String especialidad);
}