package org.example.model;

import org.example.model.composite.ServicioTecnologico;

public class MantenimientoCorrectivo extends ServicioTecnologico {



    /**
     * Metodo que construye el mantenimiento correctivo
     *
     * @param nombre
     * @param codigo
     * @param descripcion
     */
    public MantenimientoCorrectivo(String nombre, String codigo, String descripcion) {
        super(nombre, codigo, descripcion, 120000.0);
    }
}
