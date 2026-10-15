package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudServicio {

    private String codigo;
    private LocalDate fechaCreacion;
    private boolean atencionPresencial;
    private double costoTotal;
    private Tecnico theTecnico;
    private Cliente theCliente;
    private PaqueteServicio thePaquete;

    /**
     * Metodo que construye la solicitud de servicio
     * @param codigo
     * @param fechaCreacion
     * @param atencionPresencial
     * @param costoTotal
     */
    public SolicitudServicio(String codigo, LocalDate fechaCreacion, boolean atencionPresencial, double costoTotal) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.atencionPresencial = atencionPresencial;
        this.costoTotal = costoTotal;
    }


    /**
     * Metodo que calcula el costo total de la solicitud de servicio
     * @return
     */
    public double calcularCostoTotal() {
        double total = 0;
         total += thePaquete.getCosto();
        if (atencionPresencial){
            total += theTecnico.getCostoDesplazamiento();
         }
        return total;
    }


    @Override
    public String toString() {
        return "SolicitudServicio{" +
                "codigo='" + codigo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", cliente=" + theCliente +
                ", elementosContratados=" + listServicios +
                ", atencionPresencial=" + atencionPresencial +
                ", costoTotal=" + costoTotal +
                '}';
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isAtencionPresencial() {
        return atencionPresencial;
    }

    public void setAtencionPresencial(boolean atencionPresencial) {
        this.atencionPresencial = atencionPresencial;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Tecnico getTheTecnico() {
        return theTecnico;
    }

    public void setTheTecnico(Tecnico theTecnico) {
        this.theTecnico = theTecnico;
    }

    public Cliente getTheCliente() {
        return theCliente;
    }

    public void setTheCliente(Cliente theCliente) {
        this.theCliente = theCliente;
    }

    public List<ServicioTecnologico> getElementosContratados() {
        return listServicios;
    }

    public void setElementosContratados(List<ServicioTecnologico> listServicios) {
        this.listServicios = listServicios;
    }
}
