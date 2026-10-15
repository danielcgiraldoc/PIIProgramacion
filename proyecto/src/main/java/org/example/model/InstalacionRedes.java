package org.example.model;

import org.example.model.composite.ServicioTecnologico;

public class InstalacionRedes extends ServicioTecnologico {



    /**
     * Metodo que construye la clase InstalacionRedes
     * @param nombre
     * @param codigo
     * @param descripcion
     */
    public InstalacionRedes(String nombre, String codigo, String descripcion) {
        super(nombre, codigo, descripcion, 180000);
    }






}
