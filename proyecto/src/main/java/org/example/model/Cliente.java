package org.example.model;

import java.time.LocalDate;

public class Cliente{

    private String documento;
    private String nombre;
    private String correo;
    private LocalDate fechaProgramada;
    private String observaciones;
    private boolean prioridad;


    /**
     * Constructor para el cliente
     * @param builder
     */
    public Cliente (Builder builder) {
        this.documento = builder.documento;
        this.nombre = builder.nombre;
        this.correo = builder.correo;
        this.fechaProgramada = builder.fechaProgramada;
        this.observaciones = builder.observaciones;
        this.prioridad = builder.prioridad;

    }


    /**
     * Clase Builder
     */
    public static class Builder {

        private String documento;
        private String nombre;
        private String correo;
        private LocalDate fechaProgramada;
        private String observaciones;
        private boolean prioridad;

        /**
         * Metodo que obliga tener los atributos necesarios al momento de construir el objeto
         * @param documento
         * @param nombre
         * @param correo
         */
        public Builder(String documento, String nombre, String correo) {
            this.documento = documento;
            this.nombre = nombre;
            this.correo = correo;
        }





        /**
         * Metodo constructor de las observaciones
         * @param observaciones
         * @return
         */
        public Builder observaciones (String observaciones){
            this.observaciones = observaciones;
            return this;
        }

        /**
         * Metodo constructor de la fecha
         * @param fechaProgramada
         * @return
         */
        public Builder fechaProgramada (LocalDate fechaProgramada){
            this.fechaProgramada = fechaProgramada;
            return this;
        }

        /**
         * Metodo builder para cliente
         * @return
         */
        public Cliente build () {
            return new Cliente(this);
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "documento='" + documento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", fechaProgramada=" + fechaProgramada +
                ", observaciones='" + observaciones + '\'' +
                ", prioridad=" + prioridad +
                '}';
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isPrioridad() {
        return prioridad;
    }

    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }
}
