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

    /**
     * Metodo para definir el precio de la clase obligando a implementar el metodo de la clase padre
     * @return
     */
    @Override
    public double calcularCosto() {
        return 180000;
    }
}
