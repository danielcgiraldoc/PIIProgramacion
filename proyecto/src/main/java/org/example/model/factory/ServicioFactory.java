package org.example.model.factory;

import org.example.model.composite.ServicioTecnologico;

public abstract class ServicioFactory {

    /**
     * Metodo para crear un servicio tecnologico
     * @param nombre
     * @param codigo
     * @param descripcion
     * @return
     */
    public abstract ServicioTecnologico crearServicio(String nombre, String codigo, String descripcion);
}