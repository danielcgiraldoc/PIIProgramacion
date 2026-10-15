package org.example.model;

public abstract class ServicioTecnologico {

    protected String nombre;
    protected String codigo;
    protected String descripcion;
    protected double costoBase;


    /**
     * Metodo que construye el servicio tecnologico
     * @param nombre
     * @param codigo
     * @param descripcion
     * @param costoBase
     */
    public ServicioTecnologico(String nombre, String codigo, String descripcion, double costoBase) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
    }
}
