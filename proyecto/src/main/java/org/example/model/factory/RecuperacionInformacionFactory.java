package org.example.model.factory;

import org.example.model.RecuperacionInformacion;
import org.example.model.ServicioTecnologico;

public class RecuperacionInformacionFactory extends ServicioFactory {

    /**
     * Metodo que permite crear la clase RecuperacionInformacion
     * @param nombre
     * @param codigo
     * @param descripcion
     * @return
     */
    @Override
    public ServicioTecnologico crearServicio(String nombre, String codigo, String descripcion) {
        return new RecuperacionInformacion(nombre, codigo, descripcion);
    }
}