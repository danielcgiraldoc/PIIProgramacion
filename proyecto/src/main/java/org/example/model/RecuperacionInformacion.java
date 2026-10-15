package org.example.model;

import org.example.model.composite.ServicioTecnologico;

public class RecuperacionInformacion extends ServicioTecnologico {


    /**
     * Metodo que construye la clase RecuperacionInformacion
     * @param nombre
     * @param codigo
     * @param descripcion
     */
    public RecuperacionInformacion(String nombre, String codigo, String descripcion) {
        super(nombre, codigo, descripcion, 150000);
    }
}
