package org.example.model;

public class RecuperacionInformacion extends ServicioTecnologico{


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
