package org.example.model;

import org.example.model.composite.ServicioTecnologico;

public class MantenimientoPreventivo extends ServicioTecnologico {


    /**
     * Metodo que construye el MantenimientoPreventivo
     *
     * @param nombre
     * @param codigo
     * @param descripcion
     */
    public MantenimientoPreventivo(String nombre, String codigo, String descripcion) {
        super(nombre, codigo, descripcion, 80000);
    }

    /**
     * Metodo para definir el precio de la clase obligando a implementar el metodo de la clase padre
     * @return
     */
    @Override
    public double calcularCosto() {
        return 80000;
    }
}
