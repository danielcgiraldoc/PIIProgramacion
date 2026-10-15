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



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }
}
