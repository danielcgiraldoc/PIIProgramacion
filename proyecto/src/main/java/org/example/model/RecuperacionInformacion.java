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

    /**
     * Metodo para definir el precio de la clase obligando a implementar el metodo de la clase padre
     * @return
     */
    @Override
    public double calcularCosto() {
        return 150000;
    }
}
