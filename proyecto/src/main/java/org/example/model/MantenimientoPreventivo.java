package org.example.model;

public class MantenimientoPreventivo extends ServicioTecnologico{


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

}
